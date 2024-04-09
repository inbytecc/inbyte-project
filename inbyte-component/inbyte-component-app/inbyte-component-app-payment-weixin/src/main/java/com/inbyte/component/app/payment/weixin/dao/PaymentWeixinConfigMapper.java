package com.inbyte.component.app.payment.weixin.dao;

import com.inbyte.component.app.payment.weixin.model.PaymentWeixinConfigBrief;

/**
 * 微信支付
 *
 * @author chenjw
 * @date 2022-11-21 14:19:14
 */
public interface PaymentWeixinConfigMapper {

    /**
     * 查询支付配置信息
     *
     * @param weixinPaymentMerchantId
     **/
    PaymentWeixinConfigBrief brief(String weixinPaymentMerchantId);

}