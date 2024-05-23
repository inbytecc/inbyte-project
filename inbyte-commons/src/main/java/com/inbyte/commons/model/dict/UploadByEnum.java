package com.inbyte.commons.model.dict;

/**
 * 上传源
 *
 * @author chenjw
 * @date 2023/03/14
 */
public enum UploadByEnum {

    Merchant("商户"),
    User("用户"),
    ;

    public final String name;

    UploadByEnum(String name) {
        this.name = name;
    }
}
