package com.inbyte.component.app.user.model;

/**
 * 邮箱忘记密码配置
 */
public class EmailForgetPwdProperties {

    /**
     * 忘记密码标题
     */
    private String forgetPwdTitle;
    /**
     * 忘记密码内容
     */
    private String forgetPwdContent;

    /**
     * 注册标题
     */
    private String registerTitle;
    /**
     * 注册内容
     */
    private String registerContent;

    public String getForgetPwdTitle() {
        return forgetPwdTitle;
    }

    public void setForgetPwdTitle(String forgetPwdTitle) {
        this.forgetPwdTitle = forgetPwdTitle;
    }

    public String getForgetPwdContent() {
        return forgetPwdContent;
    }

    public void setForgetPwdContent(String forgetPwdContent) {
        this.forgetPwdContent = forgetPwdContent;
    }

    public String getRegisterTitle() {
        return registerTitle;
    }

    public void setRegisterTitle(String registerTitle) {
        this.registerTitle = registerTitle;
    }

    public String getRegisterContent() {
        return registerContent;
    }

    public void setRegisterContent(String registerContent) {
        this.registerContent = registerContent;
    }
}
