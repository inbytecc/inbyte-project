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
    private String merchantId;
    /**
     * 微信服务商公众号APPID
     */
    private String appId;
    /**
     * 商户API私钥路径
     */
    private String privateKey;
    /**
     * 商户证书序列号
     */
    private String serialNumber;
    /**
     * 商户APIV3密钥
     */
    private String apiV3Key;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }
}