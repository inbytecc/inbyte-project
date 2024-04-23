package com.inbyte.component.app.order.model.refund;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单退款记录记录表详情
 *
 * @author chenjw
 * @date 2023-01-06 11:42:22
 **/
@Getter
@Setter
public class OrderRefundDetail {

    /** 退款表id */
    private Integer refundId;

    /** 退款表订单号 */
    private String refundNo;

    /** 退款原因 */
    private String refundReason;

    /** 退款金额 */
    private BigDecimal refundAmount;

    /** 用户id */
    private Integer userId;

    /** 原订单订单号 */
    private String orderNo;

    /** 退款商户号 */
    private String paymentMerchantNo;

    /** 第三方平台订单号 */
    private String paymentNo;

    /** 支付平台类型 */
    private Integer paymentType;

    /** 第三方平台退款订单号 */
    private String paymentRefundNo;

    /** 订单类型 */
    private Integer orderType;

    /** 退款申请时间 */
    private LocalDateTime refundApplyTime;

    /** 退款成功时间 */
    private LocalDateTime refundSuccessTime;

    /** 退款订单创建时间 */
    private LocalDateTime createTime;
}