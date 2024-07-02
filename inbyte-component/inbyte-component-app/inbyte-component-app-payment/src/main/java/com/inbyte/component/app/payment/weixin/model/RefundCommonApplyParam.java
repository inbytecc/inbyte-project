package com.inbyte.component.app.payment.weixin.model;


import com.inbyte.commons.model.enums.PaymentTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 杭州易思网络
 *
 * @author chenjw
 * @date 2016年06月29日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundCommonApplyParam implements Serializable {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 退款编号
     */
    private String refundNo;

    /**
     * 是否全额退款
     */
    @NotNull(message = "是否全额退款不能为空")
    private Integer fullAmountRefund;

    /**
     * 支付方式
     */
    private PaymentTypeEnum paymentType;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 应用服务地址
     */
    private String appServer;

}
