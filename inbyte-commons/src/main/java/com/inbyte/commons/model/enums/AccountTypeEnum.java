package com.inbyte.commons.model.enums;

/**
 * 账户类型
 *
 * @author chenjw
 * @date 2023/03/14
 */
public enum AccountTypeEnum {

    MERCHANT("商户"),
    USER("用户"),
//    PERSONAL("个人"),
    ;

    public final String name;

    AccountTypeEnum(String name) {
        this.name = name;
    }
}
