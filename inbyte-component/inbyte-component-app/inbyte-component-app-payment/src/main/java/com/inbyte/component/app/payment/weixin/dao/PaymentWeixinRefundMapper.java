package com.inbyte.component.app.payment.weixin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.payment.weixin.model.PaymentWeixinRefundBrief;
import com.inbyte.component.app.payment.weixin.model.PaymentWeixinRefundPo;

/**
 * 微信支付生命周期
 *
 * 表名：  payment_weixin_refund
 * @author chenjw
 * @date 2023-01-10 09:21:00
 */
public interface PaymentWeixinRefundMapper extends BaseMapper<PaymentWeixinRefundPo> {

    /**
     * 根据主键动态修改
     *
     * @param update
     * @return int 修改条数
     **/
    int updateByRefundNo(PaymentWeixinRefundPo update);

    /**
     * 概要
     *
     * @param id
     * @return WeixinPaymentRefund
     **/
    PaymentWeixinRefundBrief brief(Integer id);

}