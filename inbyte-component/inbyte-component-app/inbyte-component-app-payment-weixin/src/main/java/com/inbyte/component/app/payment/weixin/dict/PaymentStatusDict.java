package com.inbyte.component.app.payment.weixin.dict;

/**
 * 支付状态
 * 杭州易思网络
 * @author chenjw
 * @date 2016年08月15日
 */
public enum PaymentStatusDict {

    WAIT_PAY("未支付"),
    PAID( "已支付"),
    REFUNDING( "退款处理中"),
    REFUNDED("已退款");

    public final String name;

    PaymentStatusDict(String name) {
        this.name = name;
    }

}