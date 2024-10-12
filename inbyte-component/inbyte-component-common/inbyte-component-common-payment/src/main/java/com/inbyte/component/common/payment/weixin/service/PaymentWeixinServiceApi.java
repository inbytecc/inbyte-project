package com.inbyte.component.common.payment.weixin.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.common.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.common.payment.weixin.model.*;
import com.wechat.pay.java.service.refund.model.Refund;

/**
 * 微信支付服务
 *
 * @author chenjw
 * @date 2023/1/9
 */
public interface PaymentWeixinServiceApi {


    /**
     * 发起预付单
     * <p>
     * 微信预付单接口文档：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml
     *
     * @param prepaidOrderParam
     * @return
     */
    R<PaymentWeixinPrepayDto> prepayOrder(PaymentWeixinPrepayParam prepaidOrderParam, String weixinPaymentId);


    /**
     * 查询支付状态
     *
     * @param orderNo
     * @return
     */
    R<PaymentSuccessNotifyParam> queryPaymentStatus(String orderNo);

    /**
     * 退款申请
     * 微信支付退款 API 接口文档： https://pay.weixin.qq.com/wiki/doc/apiv3_partner/open/pay/chapter2_4.shtml
     *
     * @param param
     * @return
     */
    R<Refund> refundApply(RefundCommonApplyParam param);

    /**
     * 微信支付异步通知
     * 对微信的支付回调进行验签, 以及处理相关的业务逻辑
     *
     * @ref https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay4_1.shtml
     **/
    R<PaymentSuccessNotifyParam> paymentSuccessVerify(PaymentWeixinSuccessVerifyParam param);

    /**
     * 退款成功异步通知验证
     *
     * @param param
     * @return
     */
    R<RefundSuccessNotifyParam> refundSuccessVerify(RefundWeixinSuccessVerifyParam param);

    R close(String orderNo);
}
