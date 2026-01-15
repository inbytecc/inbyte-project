package com.inbyte.component.common.aliyun.oss.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * OSS POST签名返回对象
 * 用于Web端服务端签名直传
 *
 * @author chenjw
 * @date 2024/12/27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AliyunOssPostSignatureDto {

    /**
     * 签名版本，固定值为 OSS4-HMAC-SHA256
     */
    @JsonProperty("x_oss_signature_version")
    private String ossSignatureVersion;

    /**
     * Base64编码的Policy字符串
     */
    private String policy;

    /**
     * 派生密钥的参数集
     * 格式: accessKeyId/date/region/oss/aliyun_v4_request
     */
    @JsonProperty("x_oss_credential")
    private String xOssCredential;

    /**
     * 请求时间
     * 格式: yyyyMMddTHHmmssZ (ISO 8601)
     */
    @JsonProperty("x_oss_date")
    private String xOssDate;

    /**
     * 签名认证描述信息
     */
    private String signature;

    /**
     * 安全令牌 (STS Token)
     */
    @JsonProperty("security_token")
    private String securityToken;

    /**
     * 上传路径，完整路径（包含文件名）
     */
    private String uploadPath;

    /**
     * OSS Bucket域名
     */
    private String host;
}
