package com.inbyte.component.app.order.event;

import com.inbyte.commons.model.dict.AppTypeEnum;
import com.inbyte.commons.model.dict.OrderTypeEnum;
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
public class OrderPaidEvent extends ApplicationEvent {

    /**
     * 用户ID
     */
    private Integer userId;

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
     * 订单类型
     */
    private OrderTypeEnum orderType;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * appId
     */
    private String appId;

    /**
     * 商户号
     */
    private String mctNo;

    public OrderPaidEvent(Object source, Integer userId, String venueId, String orderNo, OrderTypeEnum orderType, BigDecimal orderAmount,
                          String appId, String mctNo) {
        super(source);
        this.userId = userId;
        this.venueId = venueId;
        this.orderNo = orderNo;
        this.orderType = orderType;
        this.orderAmount = orderAmount;
        this.appId = appId;
        this.mctNo = mctNo;
    }
}