package com.inbyte.component.common.payment.weixin.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 微信支付生命周期详情
 *
 * @author chenjw
 * @date 2023-01-09 21:43:52
 **/
@Getter
@Setter
public class PaymentWeixinInfoBrief {

    /** 微信支付ID */
    private Integer weixinPaymentId;

    /** 订单用户ID */
    private Integer userId;

    /** 订单编号 */
    private String orderNo;

    /** 订单概要 */
    private String orderBrief;

    /** 订单类型 */
    private String orderType;

    /** 订单图片url */
    private String mainPhoto;

    /** 场馆ID */
    private String venueId;

    /**
     * 商户号
     */
    private String mctNo;

    /** 小程序ID */
    private String appId;

    /** 实付金额 真实支付金额, 回调的数据 */
    private BigDecimal paymentAmount;

    /** 微信支付平台商户ID */
    private String weixinPaymentMerchantId;

    /** 已支付 */
    private Integer paid;

    /** 第三方平台订单号 */
    private String paymentNo;


}