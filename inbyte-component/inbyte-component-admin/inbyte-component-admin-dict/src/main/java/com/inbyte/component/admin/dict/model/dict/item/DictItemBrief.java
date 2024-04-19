package com.inbyte.component.admin.dict.model.dict.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 字典项摘要
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Getter
@Setter
public class DictItemBrief {

    /** 条目ID */
    private Integer itemId;

    /** 字典ID */
    private Integer dictId;

    /** 字典名字 */
    private String dictName;

    /** 字典编码 */
    private String code;

    /** 名字 */
    private String name;

    /** ICON */
    private String icon;

    /** 备注 */
    private String remark;

    /** 排序 */
    private Integer ordinal;

    /** 日期 */
    private LocalDateTime createTime;

}
