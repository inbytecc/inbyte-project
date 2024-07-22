package com.inbyte.commons.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 商品退单事件
 * @author: chenjw
 * @create: 2024-04-05 16:35
 */
@Getter
@Setter
public class OrderCancelEvent extends ApplicationEvent {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单bizId
     */
    private String orderSn;


    public OrderCancelEvent(Object source) {
        super(source);
    }
}
