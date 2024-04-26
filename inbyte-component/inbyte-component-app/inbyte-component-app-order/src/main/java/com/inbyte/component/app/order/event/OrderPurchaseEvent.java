package com.inbyte.component.app.order.event;

import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

/**
 * 二维码分享下单通知
 *
 * @author chenjw
 * @date 2022-11-28 14:42:12
 **/
@Getter
@Setter
public class OrderPurchaseEvent extends ApplicationEvent {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 外部用户ID
     */
    private Integer eid;

    /**
     * 应用类型
     */
    private AppTypeEnum appType;

    /**
     * 场馆ID
     */
    private String venueId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    public OrderPurchaseEvent(Object source, Integer userId, Integer eid, String venueId, String orderNo, BigDecimal orderAmount) {
        super(source);
        this.userId = userId;
        this.eid = eid;
        this.venueId = venueId;
        this.orderNo = orderNo;
        this.orderAmount = orderAmount;
    }
}