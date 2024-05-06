package com.inbyte.component.admin.system.dict.model.dict.item;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;


/**
 * 字典项修改
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Getter
@Setter
public class DictItemUpdate {

    /** 条目ID */
    @NotNull(message = "条目ID不能为空")
    private Integer itemId;

    /** 字典ID */
    private Integer dictId;

    /** 父节点ID */
    private Integer parentId;

    /** 字典编码 */
    private String code;

    /** 名字 */
    @Length(max = 255, message = "名字长度不能超过255位")
    private String name;

    /** 备注 */
    @Length(max = 255, message = "备注长度不能超过255位")
    private String remark;

    /** 排序 */
    private BigDecimal ordinal;

}
