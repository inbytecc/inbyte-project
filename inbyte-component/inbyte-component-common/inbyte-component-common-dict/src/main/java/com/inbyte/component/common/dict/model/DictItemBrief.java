package com.inbyte.component.common.dict.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    /** 父节点 */
    private Integer parentId;

    /** 字典编码 */
    private Integer code;

    /** 名字 */
    private String name;

    /** 排序 */
    private Integer ordinal;

    private List<DictItemBrief> children;
}
