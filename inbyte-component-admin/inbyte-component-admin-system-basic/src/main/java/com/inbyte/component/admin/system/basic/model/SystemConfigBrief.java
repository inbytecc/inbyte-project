package com.inbyte.component.admin.system.basic.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 系统配置摘要
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@Getter
@Setter
public class SystemConfigBrief {

    /** 配置id */
    private Integer id;

    /** 配置项key */
    private String key;

    /** 配置值 */
    private String value;

    /** 开放 */
    private Integer open;

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

}
