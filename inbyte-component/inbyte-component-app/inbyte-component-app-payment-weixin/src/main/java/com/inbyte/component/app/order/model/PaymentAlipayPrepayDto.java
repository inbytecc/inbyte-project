package com.inbyte.component.app.order.model;

import lombok.*;

/**
 * 生成支付单
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentAlipayPrepayDto {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 支付编号
     */
    private String paymentNo;
}
