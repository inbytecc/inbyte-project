package com.inbyte.component.app.user.model.app;


import jakarta.validation.constraints.NotNull;

/**
 * App小程序签名参数
 *
 * @author chenjw
 */
public class AppSignParam {

    /**
     * AppID 应用ID
     */
    @NotNull(message = "应用 ID 不能为空")
    private String appId;

    /**
     * AppID 应用ID
     */
    private Integer appType;

    /**
     * 版本号
     */
    private String AppVersion;

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

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }
}
