package com.inbyte.component.common.payment.weixin.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 微信支付生命周期摘要
 *
 * @author chenjw
 * @date 2023-01-10 09:21:00
 **/
@Getter
@Setter
public class PaymentWeixinRefundBrief {

    /** 微信退款ID */
    private Integer weixinRefundId;

    /** 微信支付ID */
    private Integer weixinPaymentId;

    /** 订单用户ID */
    private Integer userId;

    /** 订单编号 */
    private String orderNo;

    /** 商户ID */
    private String mctNo;

    /** 小程序ID */
    private String appId;

    /** 微信支付平台商户ID */
    private String weixinPaymentMerchantId;

    /** 实付金额 真实支付金额, 回调的数据 */
    private BigDecimal paymentAmount;

    /** 第三方平台订单号 */
    private String paymentNo;

    /** 退款金额 */
    private BigDecimal refundApplyAmount;

    /** 退款申请时间 */
    private LocalDateTime refundApplyTime;

    /** 回调通知退款金额 */
    private BigDecimal notifyRefundAmount;

    /** 退款完成时间 */
    private LocalDateTime refundFinishTime;

    /** 已退款 */
    private Integer refunded;

    /** 订单创建时间 */
    private LocalDateTime createTime;
}