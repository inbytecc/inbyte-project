package com.inbyte.component.admin.system.config.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;
import com.alibaba.fastjson2.JSONArray;

/**
 * 系统配置创建
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@Getter
@Setter
public class SystemConfigInsert {

    /** 配置项key */
    @NotNull(message = "配置项key不能为空")
    @Length(max = 64, message = "配置项key长度不能超过64位")
    private String key;

    /** 配置值 */
    @NotNull(message = "配置值不能为空")
    @Length(max = 65535, message = "配置值长度不能超过65535位")
    private String value;

    /** 配置项描述字段 */
    @Length(max = 512, message = "配置项描述字段长度不能超过512位")
    private String remark;

}
