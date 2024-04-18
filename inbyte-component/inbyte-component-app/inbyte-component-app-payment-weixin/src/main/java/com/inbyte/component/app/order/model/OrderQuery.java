package com.inbyte.component.app.order.model;

import com.inbyte.commons.model.dto.BasePage;

/**
 * 订单查询参数
 *
 * @author chenjw
 */
public class OrderQuery extends BasePage {

    /**
     * 订单状态类别0：全部、 1：待支付；2：已支付；3：退款
     */
    private Integer orderStatusType;

    public Integer getOrderStatusType() {
        return orderStatusType;
    }

    public void setOrderStatusType(Integer orderStatusType) {
        this.orderStatusType = orderStatusType;
    }

}
