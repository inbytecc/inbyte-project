package com.inbyte.component.app.order.service;

import com.inbyte.commons.model.dict.OrderTypeEnum;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.order.model.refund.RefundApplyParam;
import com.inbyte.component.app.payment.common.model.PaymentSuccessNotifyParam;
import com.inbyte.component.app.payment.common.model.RefundSuccessNotifyParam;
import com.inbyte.component.app.payment.weixin.model.PaymentWeixinPrepayDto;

/**
 * 订单中心通用接口
 * 杭州易思网络
 *
 * @author chenjw
 * @date 2016年05月06日
 */
public interface OrderServiceCommonApi {


    /**
     * 取消订单
     *
     * @param orderNo
     * @return
     */
    R cancel(String orderNo);

    /**
     * 微信预支付
     * 签名
     */
    R<PaymentWeixinPrepayDto> weixinPrepay(String orderNo);

    /**
     * 订单付款完成后支付平台异步通知调用的接口
     *
     * @param paymentSuccessNotifyParam
     * @return R
     */
    R paymentSuccessNotify(PaymentSuccessNotifyParam paymentSuccessNotifyParam);

    /**
     * 申请退款
     *
     * @param refundApplyParam
     * @return
     */
    R refundApply(RefundApplyParam refundApplyParam);

    /**
     * 退款成功
     * <p>
     * 支付平台退款成功回调接口, 同步订单信息
     *
     * @param refundSuccessNotifyParam 退款成功返回数据
     * @return
     */
    R refundSuccessNotify(RefundSuccessNotifyParam refundSuccessNotifyParam);

//    /**
//     * 退款申请页面
//     * @param orderNo
//     * @return
//     */
//    R<OrderRefundIndexDto> refundIndex(String orderNo);

    OrderTypeEnum getOrderType();
}
