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
     * 是否全额退款
     */
    @NotNull(message = "是否全额退款不能为空")
    private Integer fullAmountRefund;

    /**
     * 退款金额
     */
//    @NotNull(message = "退款金额不能为空")
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
//    @NotBlank(message = "退款原因不能为空")
    private String refundReason;
}
