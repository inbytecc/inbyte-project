package com.inbyte.component.app.payment.weixin.model;

import com.inbyte.component.app.payment.weixin.dict.PaymentTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 付款成功回调
 *
 * 杭州易思网络
 * @author chenjw
 * @date 2016年05月06日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSuccessDto {

    /**
     * 支付平台商户ID
     */
    private String paymentMerchantId;
    /**
     * 商户订单号
     */
    private String orderNo;
    /**
     * 付款平台类型
     */
    private PaymentTypeEnum paymentTypeEnum;
    /**
     * 交易金额
     */
    private BigDecimal paymentAmount;
    /**
     * 付款用户ID
     */
    private String paymentUserId;
    /**
     * 平台订单号
     */
    private String paymentNo;
    /**
     * 付款用户名
     */
    private String paymentUserName;
    /**
     * 付款时间
     */
    private LocalDateTime paymentTime;

}
