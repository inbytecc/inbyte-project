package com.inbyte.component.app.aliyun.oss.model;

/**
 * 上传源
 *
 * @author chenjw
 * @date 2023/03/14
 */
public enum UploadSourceDict {

    Merchant(0, "商户"),
    User(1, "用户"),
    ;

    public final int code;
    public final String name;

    UploadSourceDict(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
