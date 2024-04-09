package com.inbyte.component.admin.marketing.model.qrcode;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商户二维码摘要
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
@Getter
@Setter
public class QrcodeMerchantBrief {

    /** 宣传码ID */
    private Integer qcid;

    /** 名称 */
    private String name;

    /** 投入成本 */
    private BigDecimal investCost;

    /** 线上广告 */
    private Integer onlineAd;

    /** 宣传单页数量 */
    private Integer adPageCount;

    /** 页面路径 */
    private String page;

    /** 页面参数 */
    private String scene;

    /** 备注 */
    private String remark;

    /** 访问次数 */
    private Integer viewCount;

    /** 关联数量 */
    private Integer relationCount;

    /** 注册数量 */
    private Integer registerCount;

    /** 线索数量 */
    private Integer clueCount;

    /** 订单数量 */
    private Integer orderCount;

    /** 交易总额 */
    private BigDecimal tradeAmount;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 创建时间 */
    private LocalDateTime createTime;

}
