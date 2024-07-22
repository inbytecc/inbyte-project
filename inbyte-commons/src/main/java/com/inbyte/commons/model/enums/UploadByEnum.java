package com.inbyte.commons.model.enums;

/**
 * 上传源
 *
 * @author chenjw
 * @date 2023/03/14
 */
public enum UploadByEnum {

    MERCHANT("商户"),
    USER("用户"),
    ;

    public final String name;

    UploadByEnum(String name) {
        this.name = name;
    }
}
