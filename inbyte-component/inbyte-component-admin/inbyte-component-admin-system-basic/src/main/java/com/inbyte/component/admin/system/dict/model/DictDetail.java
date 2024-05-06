package com.inbyte.component.admin.system.dict.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 字典详情
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Getter
@Setter
public class DictDetail {

    /**
     * ID
     */
    private Integer dictId;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 英文名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 已删除
     */
    private Integer deleted;

    /**
     * 系统字典
     */
    private Integer system;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String modifier;

}
