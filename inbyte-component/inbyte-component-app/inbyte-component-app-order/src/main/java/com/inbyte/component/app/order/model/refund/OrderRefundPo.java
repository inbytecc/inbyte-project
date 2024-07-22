package com.inbyte.component.app.order.model.refund;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.enums.OrderTypeEnum;
import com.inbyte.commons.model.enums.PaymentTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单退款记录记录表实体
 *
 * @author chenjw
 * @date 2023-03-22 15:38:56
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("order_refund")
public class OrderRefundPo {

    /**
     * 退款ID
     */
    @TableId(value = "refundId", type = IdType.AUTO)
    private Integer refundId;

    /**
     * 退款表订单号
     */
    private String refundNo;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 原订单订单号
     */
    private String orderNo;

    /**
     * 退款商户号
     */
    private String paymentMerchantNo;

    /**
     * 第三方平台订单号
     */
    private String paymentNo;

    /**
     * 支付平台类型
     */
    private PaymentTypeEnum paymentPlatformType;

    /**
     * 第三方平台退款订单号
     */
    private String paymentRefundNo;

    /**
     * 订单类型
     */
    private OrderTypeEnum orderType;

    /**
     * 退款申请时间
     */
    private LocalDateTime refundApplyTime;

    /**
     * 退款成功时间
     */
    private LocalDateTime refundSuccessTime;

    /**
     * 场馆ID
     */
    private String venueId;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 退款订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
