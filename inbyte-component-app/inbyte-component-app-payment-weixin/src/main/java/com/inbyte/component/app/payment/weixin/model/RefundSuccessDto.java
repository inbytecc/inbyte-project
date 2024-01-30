package com.inbyte.component.app.payment.weixin.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退款成功回调
 * 杭州湃橙体育科技有限公司
 *
 * @author chenjw
 * @date: 2016/10/13 12:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundSuccessDto implements Serializable {
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
