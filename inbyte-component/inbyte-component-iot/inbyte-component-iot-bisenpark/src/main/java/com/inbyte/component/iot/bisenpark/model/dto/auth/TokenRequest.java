package com.inbyte.component.iot.bisenpark.model.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取Token请求参数
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
@Builder
public class TokenRequest {

    /**
     * 随机数串
     */
    private String randomStr;

    /**
     * 固定为server
     */
    private String scope;

    /**
     * 固定:client_credentials
     */
    private String grantType;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 创建默认请求参数
     */
    public static TokenRequest create(String clientId, String clientSecret) {
        return TokenRequest.builder()
                .randomStr(String.valueOf(System.currentTimeMillis()))
                .scope("server")
                .grantType("client_credentials")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
} 