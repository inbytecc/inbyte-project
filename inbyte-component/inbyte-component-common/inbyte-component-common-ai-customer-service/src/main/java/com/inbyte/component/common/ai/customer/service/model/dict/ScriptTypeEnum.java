package com.inbyte.component.common.ai.customer.service.model.dict;

/**
 * 客服话术类别
 *
 * @author chenjw
 * @date: 2016/10/24 15:24
 */
public enum ScriptTypeEnum {

    PRE_SALES("售前咨询"),
    AFTER_SALE("售后服务"),
    COMPLAINT("投诉处理"),
    FAQ("常见问题");

    public final String name;

    ScriptTypeEnum(String name) {
        this.name = name;
    }
}
