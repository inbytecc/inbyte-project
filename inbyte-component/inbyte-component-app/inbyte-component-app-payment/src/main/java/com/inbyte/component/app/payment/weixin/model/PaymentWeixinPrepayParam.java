package com.inbyte.component.app.payment.weixin.model;

import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.OrderTypeEnum;
import lombok.*;

import java.math.BigDecimal;

/**
 * 微信预付单商户所需传递参数
 *
 * @author chenjw
 * @date 2022/11/14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentWeixinPrepayParam {

    /**
     * 小程序ID
     */
    private String appId;

    /**
     * 应用类型
     */
    private AppTypeEnum appType;

    /**
     * 微信支付商户ID
     */
    private String weixinPaymentMerchantId;

    /**
     * 订单用户ID
     */
    private Integer userId;

    /**
     * 微信小程序OpenId
     */
    private String openId;

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
     * 订单图片url
     */
    private String mainPhoto;

    /**
     * 场馆ID
     */
    private String venueId;

    /**
     * 场馆ID
     */
    private String venueName;

    /**
     * 商户ID
     */
    private String mctNo;

    /**
     * 金额
     */
    private BigDecimal paymentAmount;

}
