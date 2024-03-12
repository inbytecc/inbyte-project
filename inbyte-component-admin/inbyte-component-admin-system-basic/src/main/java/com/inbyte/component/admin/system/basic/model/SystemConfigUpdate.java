package com.inbyte.component.admin.system.basic.model;

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
public class SystemConfigUpdate {

    /** 配置id */
    private Integer id;

    /** 配置项key */
    @Length(max = 64, message = "配置项key长度不能超过64位")
    private String key;

    /** 配置值 */
    @Length(max = 65535, message = "配置值长度不能超过65535位")
    private String value;

    /** 配置项描述字段 */
    @Length(max = 512, message = "配置项描述字段长度不能超过512位")
    private String remark;

}
