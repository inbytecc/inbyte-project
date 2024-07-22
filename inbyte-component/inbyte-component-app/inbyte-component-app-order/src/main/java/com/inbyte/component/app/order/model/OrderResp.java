package com.inbyte.component.app.order.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单创建返回包装
 *
 * @author chenjw
 * @date 2024-04-16 17:28:41
 **/
@Getter
@Setter
public class OrderResp {

    /**
     * 订单编号
     */
    private String orderNo;


    /**
     * 需要付款
     */
    private Integer needPayment;

    public OrderResp(String orderNo, Integer needPayment) {
        this.orderNo = orderNo;
        this.needPayment = needPayment;
    }
    public OrderResp() {
    }

}
