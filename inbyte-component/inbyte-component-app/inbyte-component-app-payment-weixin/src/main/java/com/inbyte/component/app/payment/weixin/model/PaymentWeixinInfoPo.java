package com.inbyte.component.app.payment.weixin.model;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.dict.OrderTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 微信支付交易实体
 *
 * @author chenjw
 * @date 2023-03-22 15:59:41
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("payment_weixin_info")
public class PaymentWeixinInfoPo {

    /**
     * 微信支付ID
     */
    @TableId(value = "weixin_payment_id", type = IdType.AUTO)
    private Integer weixinPaymentId;

    /**
     * 订单用户ID
     */
    private Integer userId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单概要
     */
    private String orderBrief;

    /**
     * 订单类型
     */
    private OrderTypeEnum orderType;

    /**
     * 订单图片
     */
    private String mainPhoto;

    /**
     * 场馆ID
     */
    private Integer venueId;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 小程序ID
     */
    private String appId;

    /**
     * 回调通知交易金额
     */
    private BigDecimal notifyPaymentAmount;

    /**
     * 微信支付平台商户ID
     */
    private String weixinPaymentMerchantId;

    /**
     * 微信预支付ID
     */
    private String prepayId;

    /**
     * 已支付
     */
    private Integer paid;

    /**
     * 实付金额
     */
    private BigDecimal paymentAmount;

    /**
     * 第三方平台订单号
     */
    private String paymentNo;

    /**
     * 付款人ID
     */
    private String paymentUserId;

    /**
     * 支付完成时间
     */
    private LocalDateTime paymentFinishTime;

    /**
     * 银行类型
     */
    private String bankType;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 退款金额
     */
    private BigDecimal refundTotalAmount;

    /**
     * 已申请退款
     */
    private Integer refundApplied;

    /**
     * 已退款
     */
    private Integer refunded;

    /**
     * 交易关闭时间
     */
    private LocalDateTime cancelTime;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否逻辑删除
     */
    private Integer deleted;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更多信息
     */
    private JSONObject moreInfo;
}