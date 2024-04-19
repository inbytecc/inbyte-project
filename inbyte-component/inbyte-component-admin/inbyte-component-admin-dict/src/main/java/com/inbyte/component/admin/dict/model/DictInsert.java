package com.inbyte.component.admin.dict.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 字典创建
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Getter
@Setter
public class DictInsert {


    /**
     * 字典编码
     */
    @NotNull(message = "字典编码不能为空")
    @Length(max = 32, message = "字典编码长度不能超过32位")
    private String code;

    /** 字典名 */
    @NotNull(message = "字典名不能为空")
    @Length(max = 32, message = "字典名长度不能超过32位")
    private String name;

    /** 备注 */
    @Length(max = 16, message = "备注长度不能超过16位")
    private String remark;
}
