package com.inbyte.component.app.payment.common.model;

import com.inbyte.commons.model.enums.PaymentTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付平台付款成功参数
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
public class PaymentSuccessNotifyParam {

    /**
     * 支付平台商户ID
     */
    private String paymentMerchantId;

    /**
     * app应用ID
     */
    private String appId;
    /**
     * 商户平台ID
     */
    private String merchantId;
    /**
     * 商户订单号
     */
    private String orderNo;
    /**
     * 付款平台类型
     */
    private PaymentTypeEnum paymentType;
    /**
     * 支付金额
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
