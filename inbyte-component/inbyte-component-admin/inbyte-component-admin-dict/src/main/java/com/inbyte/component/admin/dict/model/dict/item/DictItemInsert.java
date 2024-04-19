package com.inbyte.component.admin.dict.model.dict.item;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 字典项创建
 *
 * @author chenjw
 * @date 2023-12-17 22:36:34
 **/
@Getter
@Setter
public class DictItemInsert {

    /** 字典ID */
    @NotNull(message = "字典ID不能为空")
    private Integer dictId;

    /** 父节点ID */
    private Integer parentId;

    /** 字典值 */
    @NotNull(message = "字典值不能为空")
    @Length(max = 32, message = "字典值长度不能超过32位")
    private String code;

    /** 名字 */
    @Length(max = 32, message = "名字长度不能超过32位")
    private String name;

    /** ICON */
    @Length(max = 255, message = "ICON长度不能超过255位")
    private String icon;

    /** 备注 */
    @Length(max = 255, message = "备注长度不能超过255位")
    private String remark;

}
