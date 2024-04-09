package com.inbyte.component.admin.marketing.model.clue;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


/**
 * 客户线索修改
 *
 * @author chenjw
 * @date 2023-03-09 13:22:05
 **/
@Getter
@Setter
public class CustomerClueUpdate {

    /** 线索ID */
    @NotNull(message = "线索ID不能为空")
    private Integer clueId;

    /** 客户名字 */
    @Length(min = 1, max = 255, message = "客户名字长度不能超过255位")
    private String nickName;

    /** 手机号 */
    @NotNull(message = "手机号不能为空")
    @Length(min = 1, max = 11, message = "手机号长度不能超过11位")
    private String tel;

    /** 意向级别 */
    @NotNull(message = "意向级别不能为空")
    private Integer intentionLevel;

    /** 备注 */
    private String remark;

}
