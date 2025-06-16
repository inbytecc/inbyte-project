package com.inbyte.component.iot.bisenpark.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取Token响应参数
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
public class TokenResponse {

    /**
     * 请求凭证
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token类型，固定为bearer
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * 过期时间，单位秒
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    /**
     * 作用域
     */
    @JsonProperty("scope")
    private String scope;

    /**
     * 错误码（错误时返回）
     */
    @JsonProperty("error")
    private String error;

    /**
     * 错误说明（错误时返回）
     */
    @JsonProperty("error_description")
    private String errorDescription;

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return accessToken != null && !accessToken.isEmpty();
    }
} 