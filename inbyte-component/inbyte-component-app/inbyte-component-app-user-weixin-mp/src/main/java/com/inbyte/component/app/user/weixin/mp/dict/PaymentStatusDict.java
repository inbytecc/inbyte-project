package com.inbyte.component.app.user.weixin.mp.dict;

/**
 * 支付状态
 * 杭州易思网络
 * @author chenjw
 * @date 2016年08月15日
 */
public enum PaymentStatusDict {

    Waiting_For_Payment(0, "未支付"),
    Paid(1, "已支付"),
    Refunding(2, "退款处理中"),
    Refunded(3, "已退款");

    public final int code;
    public final String name;

    PaymentStatusDict(int code, String name) {
        this.code = code;
        this.name = name;
    }

}