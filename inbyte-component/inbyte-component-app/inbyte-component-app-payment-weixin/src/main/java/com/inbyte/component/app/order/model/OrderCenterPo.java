package com.inbyte.component.app.order.model;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.OrderTypeEnum;
import com.inbyte.component.app.order.dict.OrderStatusEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单汇总实体
 *
 * @author chenjw
 * @date 2024-04-16 17:28:41
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("order_center")
public class OrderCenterPo {


    /**
      * 订单编号
      */
    @TableId(value = "order_no", type = IdType.INPUT)
    private String orderNo;

    /**
      * 订单用户ID
      */
    private Integer userId;

    /**
      * 用户昵称
      */
    private String nickname;

    /**
      * 用户头像
      */
    private String avatar;

    /**
      * 手机号
      */
    private String tel;

    /**
      * 订单标题
      */
    private String orderTitle;

    /**
      * 订单概要
      */
    private String orderBrief;

    /**
      * 订单状态
      */
    private OrderStatusEnum orderStatus;

    /**
      * 订单类型
      */
    private OrderTypeEnum orderType;

    /**
      * 订单内容JSON
      */
    private JSONObject extent;

    /**
      * 订单金额 
      */
    private BigDecimal orderAmount;

    /**
      * 折扣金额
      */
    private BigDecimal payableAmount;

    /**
      * 场馆ID
      */
    private Integer venueId;

    /**
      * 场馆名称
      */
    private String venueName;

    /**
      * 商户号
      */
    private String mctNo;

    /**
      * 小程序ID
      */
    private String appId;

    /**
      * 小程序类型
      */
    private AppTypeEnum appType;

    /**
      * 订单图片
      */
    private String mainPhoto;

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

}
