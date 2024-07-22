package com.inbyte.component.app.user.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * 用户登录
 *
 * @author chenjw
 */
public class UserTelLoginParam {

    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "\\d{11}", message = "手机号不正确")
    private String tel;

    /**
     * 小程序AppId
     */
    @NotNull(message = "appId不能为空")
    private String appId;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String pwd;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
