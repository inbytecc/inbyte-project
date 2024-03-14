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
}
