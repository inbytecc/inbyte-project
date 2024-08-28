package com.inbyte.component.common.payment.weixin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 微信退款实体
 *
 * @author chenjw
 * @date 2023-03-23 11:05:25
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("payment_weixin_refund")
public class PaymentWeixinRefundPo {

    /**
     * 微信退款ID
     */
    @TableId(value = "weixinRefundId", type = IdType.AUTO)
    private Integer weixinRefundId;

    /**
     * 微信支付ID
     */
    private Integer weixinPaymentId;

    /**
     * 退款编号
     */
    private String paymentNo;

    /**
     * 退款编号
     */
    private String refundNo;

    /**
     * 支付平台退款编号
     */
    private String outRefundNo;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 订单用户ID
     */
    private Integer userId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 退款金额
     */
    private BigDecimal refundApplyAmount;

    /**
     * 退款申请时间
     */
    private LocalDateTime refundApplyTime;

    /**
     * 回调通知退款金额
     */
    private BigDecimal notifyRefundAmount;

    /**
     * 退款完成时间
     */
    private LocalDateTime refundFinishTime;

    /**
     * 已退款
     */
    private Integer refunded;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 场馆ID
     */
    private String venueId;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 是否逻辑删除
     */
    private Integer deleted;

}
