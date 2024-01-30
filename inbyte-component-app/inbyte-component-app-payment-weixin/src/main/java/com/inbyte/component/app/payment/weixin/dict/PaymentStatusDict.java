package com.inbyte.component.app.payment.weixin.dict;

/**
 * 支付状态
 * 杭州湃橙体育科技有限公司
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