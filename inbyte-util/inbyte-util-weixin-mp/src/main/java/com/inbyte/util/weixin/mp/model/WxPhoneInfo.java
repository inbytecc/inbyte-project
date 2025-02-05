package com.inbyte.util.weixin.mp.model;

import lombok.Data;

/**
 * 微信用户手机号信息
 */
@Data
public class WxPhoneInfo {
    /**
     * 用户手机号
     */
    private String phoneNumber;

    /**
     * 用户手机号（不带区号）
     */
    private String purePhoneNumber;

    /**
     * 区号
     */
    private String countryCode;
} 