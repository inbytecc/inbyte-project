package com.inbyte.commons.event;

import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

/**
 * 订单创建事件
 *
 * @author: chenjw
 * @create: 2024-04-05 16:35
 */
@Getter
@Setter
public class OrderCreateEvent extends ApplicationEvent {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户EID
     */
    private Integer eid;

    /**
     * 应用类型
     */
    private AppTypeEnum appType;

    /**
     * 场馆ID
     */
    private Integer venueId;

    /**
     * 订单编号
     */
    private String mctNo;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;


    public OrderCreateEvent(Object source) {
        super(source);
    }
}
