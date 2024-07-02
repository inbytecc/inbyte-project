package com.inbyte.commons.model.enums;

/**
 * 处理状态
 * 杭州易思网络
 * @author chenjw
 * @date 2016年08月15日
 */
public enum ProcessEnum {

    PENDING("未支付"),
    IN_PROGRESS( "处理中"),
    DONE( "已完成"),
    ;

    public final String name;

    ProcessEnum(String name) {
        this.name = name;
    }

}