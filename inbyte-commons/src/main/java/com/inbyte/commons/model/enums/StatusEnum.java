package com.inbyte.commons.model.enums;

/**
 * 通用状态字典
 * 形容：
 * 完成状态，全部、部分、无
 * 包含状态，全部、部分、无
 *
 * 杭州易思网络
 *
 * @author chenjw
 * @date 2016年08月15日
 */
public enum StatusEnum {

    NONE("无"),
    ALL("全部"),
    PARTIAL("部分"),
    ;

    public final String name;

    StatusEnum(String name) {
        this.name = name;
    }

}
