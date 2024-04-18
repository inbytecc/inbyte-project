package com.inbyte.component.app.payment.common.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退款成功返回参数
 * 杭州易思网络
 *
 * @author chenjw
 * @date: 2016/10/13 12:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundSuccessNotifyParam implements Serializable {
    /**
     * 商户订单号
     */
    private String orderNo;
    /**
     * 商户退款订单号
     */
    private String refundNo;
    /**
     * 平台订单号
     */
    private String paymentNo;
    /**
     * 支付平台退款单号
     */
    private String paymentRefundNo;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 退款入账账户
     */
    private String userReceivedAccount;
}
