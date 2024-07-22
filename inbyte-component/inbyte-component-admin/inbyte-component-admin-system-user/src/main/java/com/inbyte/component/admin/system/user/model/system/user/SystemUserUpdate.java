package com.inbyte.component.admin.system.user.model.system.user;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 用户修改
 *
 * @author chenjw
 * @date 2022-12-20 14:58:26
 **/
@Getter
@Setter
public class SystemUserUpdate {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /** 昵称 */
    private String userName;

    /** 昵称 */
    private String nickname;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private String tel;

    /** 头像 */
    private String avatar;

    /** 邮箱 */
    private String email;

    /** 备注 */
    private String remark;

    /** 需要修改密码 */
    private Integer needUpdatePwd;

    /** 角色列表 */
    private List<Integer> role;

}
