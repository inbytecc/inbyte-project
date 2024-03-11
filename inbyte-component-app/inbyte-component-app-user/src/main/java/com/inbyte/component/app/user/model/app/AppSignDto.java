package com.inbyte.component.app.user.model.app;


/**
 * App小程序接口调用凭证
 *
 * @author chenjw
 */
public class AppSignDto {

    /**
     * App接口调用凭证
     * <p>
     */
    private String appToken;

    public AppSignDto() {
    }

    public AppSignDto(String appToken) {
        this.appToken = appToken;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
