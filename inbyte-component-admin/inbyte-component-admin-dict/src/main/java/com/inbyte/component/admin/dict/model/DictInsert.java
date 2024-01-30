package com.inbyte.component.admin.dict.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;

/**
 * 字典创建
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Getter
@Setter
public class DictInsert {

    /** 英文名字 */
    @NotNull(message = "名字不能为空")
    @Length(max = 32, message = "名字长度不能超过32位")
    private String name;

    /** 标签名 */
    @Length(max = 16, message = "标签名长度不能超过16位")
    private String label;

    /** 备注 */
    @Length(max = 16, message = "备注长度不能超过16位")
    private String remark;
}
