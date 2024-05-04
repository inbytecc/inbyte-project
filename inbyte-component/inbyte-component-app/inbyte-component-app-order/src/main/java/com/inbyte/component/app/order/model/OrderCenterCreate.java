package com.inbyte.component.app.order.model;

import com.alibaba.fastjson2.JSONObject;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.OrderTypeEnum;
import lombok.*;

import java.math.BigDecimal;

/**
 * 订单汇总摘要
 *
 * @author chenjw
 * @date 2024-04-16 17:28:41
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCenterCreate {

    /** 用户ID */
    private Integer userId;

    /** 用户昵称 */
    private String nickname;

    /** 用户头像 */
    private String avatar;

    /** 用户电话 */
    private String tel;

    /** 主要照片 */
    private String mainPhoto;

    /** 订单编号 */
    private String orderNo;

    /** 订单标题 */
    private String orderTitle;

    /** 订单简介 */
    private String orderBrief;

    /** 订单类型枚举 */
    private OrderTypeEnum orderType;

    /** 订单扩展信息 */
    private JSONObject extent;

    /** 订单金额 */
    private BigDecimal orderAmount;

    /** 应付金额 */
    private BigDecimal payableAmount;

    /** 场馆ID */
    private String venueId;

    /** 场馆名称 */
    private String venueName;

    /** 商户号 */
    private String mctNo;

    /** 应用ID */
    private String appId;

    /** 应用类型枚举 */
    private AppTypeEnum appType;

}
