package com.inbyte.component.admin.system.config.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.alibaba.fastjson2.JSONArray;

/**
 * 系统配置详情
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@Getter
@Setter
public class SystemConfigDetail {

    /** 配置id */
    private Integer id;

    /** 配置项key */
    private String key;

    /** 配置值 */
    private String value;

    /** 配置项描述字段 */
    private String remark;

    /** 创建人id */
    private Integer creatorId;

    /** 创建人姓名 */
    private String creatorName;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最近更新人id */
    private Integer modifierId;

    /** 最近更新人姓名 */
    private String modifierName;

    /** 最近更新时间 */
    private LocalDateTime updateTime;

}
