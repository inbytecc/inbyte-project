package com.inbyte.component.admin.system.basic.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


/**
 * 系统配置修改
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@Getter
@Setter
public class SystemConfigUpdateByKey {

    /** 配置项key */
    @NotNull
    @Length(max = 64, message = "配置项key长度不能超过64位")
    private String key;

    /** 配置值 */
    @NotNull
    @Length(max = 65535, message = "配置值长度不能超过65535位")
    private String value;


}
