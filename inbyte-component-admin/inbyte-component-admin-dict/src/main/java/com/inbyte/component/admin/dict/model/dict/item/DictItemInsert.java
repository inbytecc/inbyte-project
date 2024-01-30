package com.inbyte.component.admin.dict.model.dict.item;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;

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

    /** 名字 */
    @Length(max = 255, message = "名字长度不能超过255位")
    private String name;

    /** 备注 */
    @Length(max = 255, message = "备注长度不能超过255位")
    private String remark;

}
