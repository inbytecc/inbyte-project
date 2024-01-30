package com.inbyte.component.app.user.dict;

/**
 * 性别
 * 杭州湃橙体育科技有限公司
 *
 * @author chenjw
 * @date 2016年08月29日
 */
public enum GenderDict {

    Unknown(0, "未知"),
    Female(1, "女"),
    Male(2, "男");

    public final int code;
    public final String name;

    GenderDict(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
