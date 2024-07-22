package com.inbyte.component.admin.system.user.model.system.user;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;

/**
 * 后台用户创建
 *
 * @author chenjw
 * @date 2023-07-25 09:47:55
 **/
@Getter
@Setter
public class SystemUserInsert {

    /** 账户名 */
    @NotNull(message = "账户名不能为空")
    @Length(max = 32, message = "账户名长度不能超过32位")
    private String userName;

    /** 昵称 */
    @Length(max = 32, message = "昵称长度不能超过32位")
    private String nickname;

    /** 真实姓名 */
    @Length(max = 32, message = "真实姓名长度不能超过32位")
    private String realName;

    /** 手机号 */
    @NotNull(message = "手机号不能为空")
    @Length(max = 11, message = "手机号长度不能超过11位")
    private String tel;

    /** 密码MD5 */
    @NotNull(message = "密码MD5不能为空")
    @Length(max = 128, message = "密码MD5长度不能超过128位")
    private String pwd;

    /** 头像 */
    @Length(max = 256, message = "头像长度不能超过256位")
    private String avatar;

    /** 邮箱 */
    @Length(max = 255, message = "邮箱长度不能超过255位")
    private String email;

}
