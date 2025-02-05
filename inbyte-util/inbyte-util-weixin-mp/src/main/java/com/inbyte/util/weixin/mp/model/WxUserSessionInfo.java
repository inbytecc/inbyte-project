package com.inbyte.util.weixin.mp.model;

import lombok.Data;

/**
 * 微信用户session信息
 */
@Data
public class WxUserSessionInfo {
    /**
     * 用户唯一标识
     */
    private String openId;

    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionId;

    /**
     * 会话密钥
     */
    private String sessionKey;
} 