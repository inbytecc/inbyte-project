package com.inbyte.component.common.payment.weixin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付合作伙伴服务商
 */
@Component
@ConfigurationProperties(prefix = "inbyte.component.common.payment.weixin.partner")
public class InbytePaymentWeixinPartnerProperties {


    /**
     * 微信服务商商户号
     */
    public static String merchantId;
    /**
     * 微信服务商公众号APPID
     */
    public static String appId;
    /**
     * 商户API私钥路径
     */
    public static String privateKey;
    /**
     * 商户证书序列号
     */
    public static String serialNumber;
    /**
     * 商户APIV3密钥
     */
    public static String apiV3Key;

    public static String getMerchantId() {
        return merchantId;
    }

    public static void setMerchantId(String merchantId) {
        InbytePaymentWeixinPartnerProperties.merchantId = merchantId;
    }

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        InbytePaymentWeixinPartnerProperties.appId = appId;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(String privateKey) {
        InbytePaymentWeixinPartnerProperties.privateKey = privateKey;
    }

    public static String getSerialNumber() {
        return serialNumber;
    }

    public static void setSerialNumber(String serialNumber) {
        InbytePaymentWeixinPartnerProperties.serialNumber = serialNumber;
    }

    public static String getApiV3Key() {
        return apiV3Key;
    }

    public static void setApiV3Key(String apiV3Key) {
        InbytePaymentWeixinPartnerProperties.apiV3Key = apiV3Key;
    }
}