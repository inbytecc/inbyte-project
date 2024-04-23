package com.inbyte.component.app.payment.weixin.model;

/**
 * 微信支付配置
 *
 * @author chenjw
 * @date 2023/1/9
 */
public class PaymentWeixinConfigBrief {

    /**
     * 微信支付商户ID
     */
    private String weixinPaymentMerchantId;
    /**
     * api V3 版本
     */
    private String apiV3Key;
    /**
     * 商户证书序列号
     */
    private String serialNumber;
    /**
     * 商户私钥 apiclient_key.pem 内容
     */
    private String merchantPrivateKey;
    /**
     * 手续费
     */
    private String serviceChargeRate;
    /**
     * 备注
     */
    private String remark;

    public String getWeixinPaymentMerchantId() {
        return weixinPaymentMerchantId;
    }

    public void setWeixinPaymentMerchantId(String weixinPaymentMerchantId) {
        this.weixinPaymentMerchantId = weixinPaymentMerchantId;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(String serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
