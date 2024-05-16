package com.inbyte.commons.model.dict;

/**
 * 订单状态
 * 杭州易思网络
 *
 * @author chenjw
 * @date 2016年08月15日
 */
public enum OrderStatusEnum {

    WAIT_PAY("待付款"),
    PAID("已支付"),
    WAIT_USE("待使用"),
    WAIT_EVALUATE("待评价"),
    // 订单流程结束
    COMPLETE("订单完成"),
    // 用户申请退款
    REFUND_APPLYING("申请退款中"),
    REFUND_REFUSED("商家拒绝退款"),
    REFUND_SUCCESS("退款成功"),
    // 未付款过期自动关闭
    NOT_PAID_EXPIRE("已关闭"),
    // 用户取消订单
    CANCELED("已取消"),
    ;

    public final String name;

    OrderStatusEnum(String name) {
        this.name = name;
    }

    /**
     * 订单状态已成交
     *
     * @return
     */
    public boolean paid() {
        return this == PAID ||
                this == WAIT_USE ||
                this == WAIT_EVALUATE;
    }
}
