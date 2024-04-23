package com.inbyte.component.app.user.weixin.mp.model.qrcode;

import lombok.*;

import java.math.BigDecimal;

/**
 * 二维码分享下单通知
 *
 * @author chenjw
 * @date 2022-11-28 14:42:12
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrCodePurchaseEventNotify {

    /** 用户ID */
    private Integer eid;
    /** 场馆ID */
    private String venueId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
}