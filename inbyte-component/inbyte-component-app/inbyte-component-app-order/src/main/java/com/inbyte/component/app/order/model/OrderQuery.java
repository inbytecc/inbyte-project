package com.inbyte.component.app.order.model;

import com.inbyte.commons.model.dto.BasePage;
import com.inbyte.commons.model.dict.OrderStatusTypeEnum;

/**
 * 订单查询参数
 *
 * @author chenjw
 */
public class OrderQuery extends BasePage {

    /**
     * 订单状态类别0：全部、 1：待支付；2：已支付；3：退款
     */
    private OrderStatusTypeEnum orderStatusType;

    public OrderStatusTypeEnum getOrderStatusType() {
        return orderStatusType;
    }

    public void setOrderStatusType(OrderStatusTypeEnum orderStatusType) {
        this.orderStatusType = orderStatusType;
    }

}
