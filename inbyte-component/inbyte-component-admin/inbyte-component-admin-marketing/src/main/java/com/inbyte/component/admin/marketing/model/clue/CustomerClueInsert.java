package com.inbyte.component.admin.marketing.model.clue;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 客户线索创建
 *
 * @author chenjw
 * @date 2023-03-09 13:22:05
 **/
@Getter
@Setter
public class CustomerClueInsert {

    /** 学员名字 */
    @Length(max = 32, message = "客户名字长度不能超过32位")
    @NotNull
    private String nickname;

    /** 手机号 */
    @NotNull(message = "手机号不能为空")
    @Length(max = 11, message = "手机号长度不能超过11位")
    private String tel;

    /** 来源类型 */
    @NotNull(message = "来源类型不能为空")
    private Integer sourceType;

    /** 意向级别 */
    @NotNull(message = "意向级别不能为空")
    private Integer intentionLevel;

    /** 跟进人ID */
    @Length(max = 32, message = "跟进人ID长度不能超过32位")
    private Integer contactPersonId;

}
