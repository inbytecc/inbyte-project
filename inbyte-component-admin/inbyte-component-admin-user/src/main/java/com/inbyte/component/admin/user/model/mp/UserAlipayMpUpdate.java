package com.inbyte.component.admin.user.model.mp;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


/**
 * 支付宝小程序用户修改
 *
 * @author chenjw
 * @date 2023-10-12 09:36:51
 **/
@Getter
@Setter
public class UserAlipayMpUpdate {

    /** 外部用户ID */
    @NotNull(message = "外部用户ID不能为空")
    private Integer eid;

    /** 备注 */
    @Length(max = 255, message = "备注长度不能超过255位")
    private String remark;

}
