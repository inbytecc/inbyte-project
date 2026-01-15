package com.inbyte.component.common.aliyun.oss;

import com.alibaba.fastjson2.JSONObject;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.inbyte.commons.exception.BizException;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.aliyun.oss.model.AliYunOssStsTokenParam;
import com.inbyte.component.common.aliyun.oss.model.AliyunOssPostSignatureDto;
import com.inbyte.component.common.aliyun.oss.model.AliyunOssProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 阿里云OSS POST签名服务
 * 用于Web端服务端签名直传
 *
 * @author chenjw
 * @date 2024/12/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliyunOssPostSignatureService {

    /**
     * STS临时凭证有效期（秒）- 固定值，默认3600秒（1小时）
     */
    private static final Long DURATION_SECONDS = 3600L;

    /**
     * 签名版本
     */
    private static final String SIGNATURE_VERSION = "OSS4-HMAC-SHA256";

    private final AliyunOssProperties aliyunOssProperties;

    /**
     * 获取POST签名
     * 用于Web端服务端签名直传
     *
     * @param param 参数
     * @return POST签名信息
     */
    public R<AliyunOssPostSignatureDto> getPostSignature(AliYunOssStsTokenParam param) {
        // 获取STS临时凭证
        AssumeRoleResponse.Credentials credentials;
        try {
            credentials = getStsCredentials();
        } catch (Exception e) {
            log.error("获取STS临时凭证失败", e);
            throw BizException.error("获取STS临时凭证失败");
        }

        String accessKeyId = credentials.getAccessKeyId();
        String accessKeySecret = credentials.getAccessKeySecret();
        String securityToken = credentials.getSecurityToken();

        // 获取当前UTC时间
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);

        // 获取日期，格式为 yyyyMMdd
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = now.format(dateFormatter);

        // 获取x-oss-date，格式为 yyyyMMddTHHmmssZ
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        String xOssDate = now.format(dateTimeFormatter);

        // 构建x-oss-credential
        String xOssCredential = String.format("%s/%s/%s/oss/aliyun_v4_request",
                accessKeyId, date, aliyunOssProperties.getRegion());

        // 构建上传目录前缀
        String uploadDir = buildUploadDir(param);

        // 创建Policy
        String policy = createPolicy(xOssCredential, xOssDate, securityToken, uploadDir);

        // 计算签名
        String signature = calculateSignature(policy, accessKeySecret, date,
                aliyunOssProperties.getRegion());

        // host格式: http://bucketname.oss-region.aliyuncs.com
        String host = "https://" + aliyunOssProperties.getBucketName() + "." + aliyunOssProperties.getEndpoint();

        AliyunOssPostSignatureDto signatureDto = AliyunOssPostSignatureDto.builder()
                .ossSignatureVersion(SIGNATURE_VERSION)
                .policy(policy)
                .xOssCredential(xOssCredential)
                .xOssDate(xOssDate)
                .signature(signature)
                .securityToken(securityToken)
                .uploadPath(uploadDir)
                .host(host)
                .build();

        return R.ok(signatureDto);
    }

    /**
     * 获取STS临时凭证
     *
     * @return STS凭证
     */
    private AssumeRoleResponse.Credentials getStsCredentials() throws Exception {
        IClientProfile profile = DefaultProfile.getProfile(aliyunOssProperties.getRegion(),
                aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        DefaultAcsClient stsClient = new DefaultAcsClient(profile);

        // 构建AssumeRole请求
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysMethod(MethodType.POST);
        request.setRoleArn(aliyunOssProperties.getRoleArn());
        request.setRoleSessionName(aliyunOssProperties.getRoleSessionName());
        request.setDurationSeconds(DURATION_SECONDS);

        // 获取STS临时凭证
        AssumeRoleResponse response = stsClient.getAcsResponse(request);
        return response.getCredentials();
    }

    /**
     * 构建上传目录前缀
     *
     * @param param 参数
     * @return 上传目录前缀
     */
    private String buildUploadDir(AliYunOssStsTokenParam param) {
        // 文件目录格式: 商户空间/商户号/年/月/日/
        ZonedDateTime now = ZonedDateTime.now();
        String uploadDir = String.format("mct-space/%s/%d/%d/%d-%s",
                param.getMctNo(),
                now.getYear(),
                now.getMonthValue(),
                new Random().nextInt(10000000),
                param.getFileName());
        return uploadDir.replace("//", "/");
    }

    /**
     * 生成过期时间
     *
     * @param seconds 有效时长（秒）
     * @return ISO8601 时间字符串，如："2014-12-01T12:00:00.000Z"
     */
    private String generateExpiration(long seconds) {
        long now = Instant.now().getEpochSecond();
        long expirationTime = now + seconds;
        Instant instant = Instant.ofEpochSecond(expirationTime);
        ZoneId zone = ZoneOffset.UTC;
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return zonedDateTime.format(formatter);
    }

    /**
     * 创建Policy
     *
     * @param xOssCredential 凭证
     * @param xOssDate       日期
     * @param securityToken  安全令牌
     * @param uploadDir      上传目录前缀
     * @return Base64编码的Policy字符串
     */
    private String createPolicy(String xOssCredential, String xOssDate,
                                String securityToken, String uploadDir) {
        Map<String, Object> policy = new HashMap<>();
        policy.put("expiration", generateExpiration(DURATION_SECONDS));

        List<Object> conditions = new ArrayList<>();

        // Bucket条件
        Map<String, String> bucketCondition = new HashMap<>();
        bucketCondition.put("bucket", aliyunOssProperties.getBucketName());
        conditions.add(bucketCondition);

        // 安全令牌条件
        Map<String, String> securityTokenCondition = new HashMap<>();
        securityTokenCondition.put("x-oss-security-token", securityToken);
        conditions.add(securityTokenCondition);

        // 签名版本条件
        Map<String, String> signatureVersionCondition = new HashMap<>();
        signatureVersionCondition.put("x-oss-signature-version", SIGNATURE_VERSION);
        conditions.add(signatureVersionCondition);

        // 凭证条件
        Map<String, String> credentialCondition = new HashMap<>();
        credentialCondition.put("x-oss-credential", xOssCredential);
        conditions.add(credentialCondition);

        // 日期条件
        Map<String, String> dateCondition = new HashMap<>();
        dateCondition.put("x-oss-date", xOssDate);
        conditions.add(dateCondition);

        // 文件大小限制 (1字节 - 10MB)
        conditions.add(Arrays.asList("content-length-range", 1, 10240000));

        // 成功状态码
        conditions.add(Arrays.asList("eq", "$success_action_status", "200"));

        // 文件前缀限制
        conditions.add(Arrays.asList("starts-with", "$key", uploadDir));

        policy.put("conditions", conditions);

        // 将Policy转换为JSON字符串
        String jsonPolicy = JSONObject.toJSONString(policy);

        // Base64编码
        return BinaryUtil.toBase64String(jsonPolicy.getBytes());
    }

    /**
     * 计算HMAC-SHA256签名
     *
     * @param key  密钥
     * @param data 数据
     * @return HMAC-SHA256结果
     */
    private byte[] hmacsha256(byte[] key, String data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            return mac.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC-SHA256", e);
        }
    }

    /**
     * 计算签名
     *
     * @param stringToSign    Base64编码的Policy字符串
     * @param accessKeySecret 访问密钥
     * @param date            日期 (yyyyMMdd)
     * @param region          地域
     * @return 签名字符串（十六进制）
     */
    private String calculateSignature(String stringToSign, String accessKeySecret,
                                      String date, String region) {
        // 步骤1: 计算dateKey
        byte[] dateKey = hmacsha256(("aliyun_v4" + accessKeySecret).getBytes(), date);

        // 步骤2: 计算dateRegionKey
        byte[] dateRegionKey = hmacsha256(dateKey, region);

        // 步骤3: 计算dateRegionServiceKey
        byte[] dateRegionServiceKey = hmacsha256(dateRegionKey, "oss");

        // 步骤4: 计算signingKey
        byte[] signingKey = hmacsha256(dateRegionServiceKey, "aliyun_v4_request");

        // 步骤5: 计算最终签名
        byte[] result = hmacsha256(signingKey, stringToSign);
        return BinaryUtil.toHex(result);
    }
}
