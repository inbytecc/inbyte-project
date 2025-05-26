package com.inbyte.component.app.order.model.refund;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退款申请参数
 *
 * @author chenjw
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundApplyParam implements Serializable {

    /**
     * 订单编号
     */
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    @NotNull(message = "退款原因不能为空")
    private String refundReason;
}
