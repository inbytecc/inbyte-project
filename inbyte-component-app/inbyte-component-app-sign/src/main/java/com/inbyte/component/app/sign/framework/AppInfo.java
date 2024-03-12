package com.inbyte.component.app.sign.framework;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * App 信息
 *
 * @author chenjw
 * @date 2022/12/31
 **/
public class AppInfo {

    /**
     * AppID 应用ID
     */
    @NotNull(message = "应用 ID 不能为空")
    private String appId;

    /**
     * AppID 应用类型
     */
    private Integer appType;

    /**
     * 版本号
     */
    private String AppVersion;

    /** 所属商户名 */
    private String mctPinyinName;

    /** 所属商户 ID */
    private String mctNo;

    /**
     * 签名时间
     */
    private LocalDateTime signTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }

    public String getMctNo() {
        return mctNo;
    }

    public void setMctNo(String mctNo) {
        this.mctNo = mctNo;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getMctPinyinName() {
        return mctPinyinName;
    }

    public void setMctPinyinName(String mctPinyinName) {
        this.mctPinyinName = mctPinyinName;
    }
}
