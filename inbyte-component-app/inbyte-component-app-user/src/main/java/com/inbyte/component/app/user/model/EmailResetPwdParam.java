package com.inbyte.component.app.user.model;


import jakarta.validation.constraints.NotNull;

/**
 * 用户账号登录
 *
 * @author chenjw
 * @date 20240307
 */
public class EmailResetPwdParam {

    /**
     * 账号
     */
    @NotNull(message = "账号不能为空")
    private String email;

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String verifyCode;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String pwd;

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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
