package com.inbyte.commons.model.dict;

/**
 * 退款状态
 *
 * @author chenjw
 * @date 2022/10/19
 */
public enum RefundStatusEnum {

    PENDING("待处理"),
    APPROVED("已批准"),
    COMPLETED("已完成"),
    REJECTED("已拒绝");

    public final String name;

    RefundStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
