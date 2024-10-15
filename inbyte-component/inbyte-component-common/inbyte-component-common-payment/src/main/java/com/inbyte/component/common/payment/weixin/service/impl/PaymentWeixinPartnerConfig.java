package com.inbyte.component.common.payment.weixin.service.impl;

import com.inbyte.component.common.payment.weixin.InbytePaymentWeixinPartnerProperties;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信合作伙伴服务商支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
@Component
@Slf4j
public class PaymentWeixinPartnerConfig implements InitializingBean {

    @Autowired
    private InbytePaymentWeixinPartnerProperties inbytePaymentWeixinPartnerProperties;

    private RSAAutoCertificateConfig config;

    /**
     * 初始化配置
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        if (inbytePaymentWeixinPartnerProperties == null) {
            return;
        }

        // 初始化商户配置
        config = new RSAAutoCertificateConfig.Builder()
                .merchantId(inbytePaymentWeixinPartnerProperties.getMerchantId())
                .privateKey(inbytePaymentWeixinPartnerProperties.getPrivateKey())
                .merchantSerialNumber(inbytePaymentWeixinPartnerProperties.getSerialNumber())
                .apiV3Key(inbytePaymentWeixinPartnerProperties.getApiV3Key())
                .build();
    }

    public RSAAutoCertificateConfig getConfig() {
        return config;
    }

    public String getAppId() {
        return inbytePaymentWeixinPartnerProperties.getAppId();
    }

    public String getWeixinPaymentMchId() {
        return inbytePaymentWeixinPartnerProperties.getMerchantId();
    }
}
