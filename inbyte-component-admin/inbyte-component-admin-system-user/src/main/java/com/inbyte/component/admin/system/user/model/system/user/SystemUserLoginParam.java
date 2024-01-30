package com.inbyte.component.admin.system.user.model.system.user;


import jakarta.validation.constraints.NotNull;

/**
 * 用户登录
 *
 * @author chenjw
 */
public class SystemUserLoginParam {

    /**
     * 账号
     * 手机号 或 用户账户
     */
    @NotNull(message = "账号不能为空")
//    @Length(value = 32, message = "账号长度超长")
    private String id;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String pwd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
