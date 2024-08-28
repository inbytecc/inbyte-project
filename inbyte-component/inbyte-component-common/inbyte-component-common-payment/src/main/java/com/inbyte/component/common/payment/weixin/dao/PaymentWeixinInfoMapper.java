package com.inbyte.component.common.payment.weixin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.common.payment.weixin.model.PaymentWeixinInfoBrief;
import com.inbyte.component.common.payment.weixin.model.PaymentWeixinInfoPo;

/**
 * 微信支付生命周期
 *
 * 表名：  payment_weixin_info
 * @author chenjw
 * @date 2023-01-09 21:43:52
 */
public interface PaymentWeixinInfoMapper extends BaseMapper<PaymentWeixinInfoPo> {


    int insertSelective(PaymentWeixinInfoPo po);

    /**
     * 概要
     *
     * @param orderNo
     * @return PaymentWeixinInfoBrief
     **/
    PaymentWeixinInfoBrief selectByNo(String orderNo);

}