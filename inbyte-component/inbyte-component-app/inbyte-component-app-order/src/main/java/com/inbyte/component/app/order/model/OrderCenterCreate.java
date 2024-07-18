package com.inbyte.component.app.order.model;

import com.alibaba.fastjson2.JSONObject;
import com.inbyte.commons.model.enums.AppTypeEnum;
import com.inbyte.commons.model.enums.OrderTypeEnum;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /** 用户昵称 */
    @NotNull(message = "用户昵称不能为空")
    private String nickname;

    /** 用户头像 */
    private String avatar;

    /** 用户电话 */
    @NotNull(message = "用户电话不能为空")
    private String tel;

    /** 主要照片 */
    @NotNull(message = "主要照片不能为空")
    private String mainPhoto;

    /** 订单编号 */
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    /** 订单标题 */
    @NotNull(message = "订单标题不能为空")
    private String orderTitle;

    /** 订单简介 */
    @NotNull(message = "订单简介不能为空")
    private String orderBrief;

    /** 订单类型枚举 */
    @NotNull(message = "订单类型不能为空")
    private OrderTypeEnum orderType;

    /** 订单扩展信息 */
    private JSONObject extent;

    /** 订单金额 */
    @NotNull(message = "订单金额不能为空")
    private BigDecimal orderAmount;

    /** 应付金额 */
    @NotNull(message = "应付金额不能为空")
    private BigDecimal payableAmount;

    /** 场馆ID */
    @NotNull(message = "场馆ID不能为空")
    private String venueId;

    /** 场馆名称 */
    @NotNull(message = "场馆名称不能为空")
    private String venueName;

    /** 商户号 */
    @NotNull(message = "商户号不能为空")
    private String mctNo;

    /** 应用ID */
    @NotNull(message = "应用ID不能为空")
    private String appId;

    /** 应用类型枚举 */
    @NotNull(message = "应用类型不能为空")
    private AppTypeEnum appType;

}
