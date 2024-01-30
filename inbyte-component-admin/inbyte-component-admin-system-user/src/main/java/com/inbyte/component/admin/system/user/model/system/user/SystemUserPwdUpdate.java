package com.inbyte.component.admin.system.user.model.system.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户密码修改
 *
 * @author chenjw
 * @date 2022-12-20 14:58:26
 **/
@Getter
@Setter
public class SystemUserPwdUpdate {

    /**
     * 密码
     */
    @NotNull
    private String pwd;

}
