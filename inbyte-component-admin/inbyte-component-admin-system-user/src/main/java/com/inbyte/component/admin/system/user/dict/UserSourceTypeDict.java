package com.inbyte.component.admin.system.user.dict;

/**
 * 用户来源类型
 *
 * @author chenjw
 * @date 2023-02-06 11:33:27
 */
public enum UserSourceTypeDict {

    User_Share(0, "小程序用户分享"),
    Merchant_Share(1, "商家分享"),
    ;

    public final int code;
    public final String name;

    UserSourceTypeDict(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
