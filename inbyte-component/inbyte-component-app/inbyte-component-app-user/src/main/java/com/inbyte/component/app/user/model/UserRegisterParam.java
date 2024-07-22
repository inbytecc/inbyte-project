package com.inbyte.component.app.user.model;


import jakarta.validation.constraints.NotNull;

/**
 * 用户账号注册
 *
 * @author chenjw
 * @date 20240307
 */
public class UserRegisterParam {

    /**
     * 账号
     */
    @NotNull(message = "账号不能为空")
    private String userName;

    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String tel;

    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空")
    private String email;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
