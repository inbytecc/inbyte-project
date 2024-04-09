package com.inbyte.component.app.user.model;


import jakarta.validation.constraints.NotNull;

/**
 * 用户账号注册
 *
 * @author chenjw
 * @date 20240307
 */
public class UserRegisterWithVerifyCodeParam extends UserRegisterParam {

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
