package com.inbyte.component.common.aliyun.oss;

import com.alibaba.fastjson2.JSONObject;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.commons.util.WebUtil;
import com.inbyte.component.common.aliyun.oss.dao.ObjectStorageMapper;
import com.inbyte.component.common.aliyun.oss.model.AliYunOssStsTokenParam;
import com.inbyte.component.common.aliyun.oss.model.AliyunOssProperties;
import com.inbyte.component.common.aliyun.oss.model.AliyunOssStsTokenDto;
import com.inbyte.component.common.aliyun.oss.model.InbyteObjectStoragePo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;

/**
 * 阿里云授权
 *
 * @author: chenjw
 * @date: 2023/3/14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliyunOssService {

    @Value("${inbyte.app.server}")
    private String server;

    private final AliyunOssProperties aliyunOssProperties;

    @Autowired
    private ObjectStorageMapper objectStorageMapper;

    /**
     * 获取STS临时凭证
     *
     * @return STS Token信息
     */
    public R<AliyunOssStsTokenDto> getStsToken(AliYunOssStsTokenParam param) {
        if (aliyunOssProperties.getRoleArn() == null || aliyunOssProperties.getRoleArn().trim().isEmpty()) {
            log.error("STS RoleArn未配置");
            return R.fail("STS配置不完整，请配置roleArn");
        }

        IClientProfile profile = DefaultProfile.getProfile(
                aliyunOssProperties.getRegion(),
                aliyunOssProperties.getAccessKeyId(),
                aliyunOssProperties.getAccessKeySecret());
        DefaultAcsClient client = new DefaultAcsClient(profile);

        try {
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(aliyunOssProperties.getRoleArn());
            request.setRoleSessionName(aliyunOssProperties.getRoleSessionName());
            request.setDurationSeconds(aliyunOssProperties.getDurationSeconds());

            // 可选：设置Policy限制访问权限
            // String policy = "{\"Version\":\"1\",\"Statement\":[{\"Effect\":\"Allow\",\"Action\":[\"oss:PutObject\",\"oss:GetObject\"],\"Resource\":[\"acs:oss:*:*:bucket-name/*\"]}]}";
            // request.setPolicy(policy);

            AssumeRoleResponse response = client.getAcsResponse(request);
            AssumeRoleResponse.Credentials credentials = response.getCredentials();

            LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(aliyunOssProperties.getDurationSeconds());
            long expiration = System.currentTimeMillis() / 1000 + aliyunOssProperties.getDurationSeconds();

            AliyunOssStsTokenDto tokenDto = AliyunOssStsTokenDto.builder()
                    .accessKeyId(credentials.getAccessKeyId())
                    .accessKeySecret(credentials.getAccessKeySecret())
                    .securityToken(credentials.getSecurityToken())
                    .expiration(expiration)
                    .expirationTime(expirationTime)
                    .bucketName(aliyunOssProperties.getBucketName())
                    .endpoint(aliyunOssProperties.getEndpoint())
                    .host("https://" + aliyunOssProperties.getBucketName() + "." + aliyunOssProperties.getEndpoint())
                    .build();

            return R.ok(tokenDto);
        } catch (Exception e) {
            log.error("获取STS Token异常", e);
            return R.fail("获取STS Token失败: " + e.getMessage());
        }
    }

    /**
     * 获取public key
     *
     * @param url
     * @return
     */
    private String executeGet(String url) {
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            @SuppressWarnings("resource")
            DefaultHttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            CloseableHttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            content = sb.toString();
        } catch (Exception e) {
            log.error("阿里云OSS, GET请求错误", e);
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    log.error("阿里云OSS, 关闭流", e);
                }
            }
            return content;
        }
    }

    /**
     * 验证上传回调的Request
     *
     * @param request
     * @param ossCallbackBody
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
            throws NumberFormatException, IOException {
        boolean ret = false;
        String authorizationInput = request.getHeader("Authorization");
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(authorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            log.info("pub key addr must be oss addrss");
            return false;
        }
        String retString = executeGet(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String queryString = request.getQueryString();
        /**
         * 特别注意： 因为接口签名时需要以 /api 前缀访问回调接口, 且 Nginx 反向代理时去掉了 /api 前缀, 导致 doCheck 验证不通过
         * 所以此处补充 /api 前缀，使校验通过
         */
        String uri = "/api" + request.getRequestURI();
        String decodeUri = URLDecoder.decode(uri, "UTF-8");
        String authStr = decodeUri;
        if (queryString != null && !queryString.equals("")) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        ret = doCheck(authStr, authorization, retString);
        return ret;
    }

    /**
     * Post请求
     */
    public void callback(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String ossCallbackBody = WebUtil.getRequestBodyString(request);
        log.info("阿里云 OSS 回调参数:{}", ossCallbackBody);
        boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
        log.info("verify result : " + ret);
        if (ret) {
            String decode = URLDecoder.decode(ossCallbackBody, "UTF-8");
            JSONObject json = StringUtil.strToJson(decode);
            String object = json.getString("object");


            InbyteObjectStoragePo inbyteObjectStoragePo = InbyteObjectStoragePo.builder()
                    .objectId(json.getInteger("objectId"))
                    .name(object.substring(object.lastIndexOf("/") + 1))
                    .path(object)
                    .mimeType(json.getString("mimeType"))
                    .height(json.getInteger("height"))
                    .width(json.getInteger("width"))
                    .size(json.getInteger("size"))
                    .uploaded(WhetherDict.Yes.code)
                    .updateTime(LocalDateTime.now())
                    .build();
            objectStorageMapper.updateById(inbyteObjectStoragePo);
        }
        if (ret) {
            response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
        } else {
            response(request, response, "{\"Status\":\"verify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * 验证RSA
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(sign);
        } catch (Exception e) {
            log.error("OSS 验证RSA异常", e);
        }

        return false;
    }

    /**
     * 服务器响应结果
     *
     * @param request
     * @param response
     * @param results
     * @param status
     * @throws IOException
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
            throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase("")) {
            response.getWriter().println(results);
        } else {
            response.getWriter().println(callbackFunName + "( " + results + " )");
        }
        response.setStatus(status);
        response.flushBuffer();
    }

}