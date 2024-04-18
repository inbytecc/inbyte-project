package com.inbyte.component.app.order.model;

import com.inbyte.component.app.order.dict.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单汇总摘要
 *
 * @author chenjw
 * @date 2024-04-16 17:28:41
 **/
@Getter
@Setter
public class OrderCenterBrief {

    /**
     * 订单No
     */
    private String orderNo;

    /**
     * 订单主图
     */
    private String mainPhoto;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单标题
     */
    private String orderTitle;

    /**
     * 订单概要
     */
    private String orderBrief;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 支付金额
     */
    private BigDecimal payableAmount;

    /**
     * 订单状态
     */
    private OrderStatusEnum orderStatus;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;
}
