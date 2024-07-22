package com.inbyte.component.admin.system.user;

import java.time.LocalDateTime;

/**
 * 会话对象
 *
 * @author chenjw
 * @date 2020/8/6
 **/
public class SessionUser {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 微信用户ID
     */
    private String openId;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * token版本
     * 用于全局校验, 极端情况可让全平台旧版本用户重新登录
     */
    private Double tokenVersion;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 商户名
     */
    private String mctName;

    /**
     * 商户名拼音
     */
    private String mctPinYinName;
    /**
     * 超管
     */
    private Integer admin;

    /**
     * 登录方式
     */
    private String loginWay;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public Double getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(Double tokenVersion) {
        this.tokenVersion = tokenVersion;
    }

    public String getMctNo() {
        return mctNo;
    }

    public void setMctNo(String mctNo) {
        this.mctNo = mctNo;
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "userId='" + userId + '\'' +
                ", tel='" + tel + '\'' +
                ", openId='" + openId + '\'' +
                ", userName='" + userName + '\'' +
                ", loginTime=" + loginTime +
                ", tokenVersion=" + tokenVersion +
                ", defaultMctNo=" + mctNo +
                '}';
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMctName() {
        return mctName;
    }

    public void setMctName(String mctName) {
        this.mctName = mctName;
    }

    public String getMctPinYinName() {
        return mctPinYinName;
    }

    public void setMctPinYinName(String mctPinYinName) {
        this.mctPinYinName = mctPinYinName;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public String getLoginWay() {
        return loginWay;
    }

    public void setLoginWay(String loginWay) {
        this.loginWay = loginWay;
    }
}
