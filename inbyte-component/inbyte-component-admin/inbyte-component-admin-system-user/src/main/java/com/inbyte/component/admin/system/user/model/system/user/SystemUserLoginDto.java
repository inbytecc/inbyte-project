package com.inbyte.component.admin.system.user.model.system.user;

/**
 * 平台用户 Token
 *
 * @author chenjw
 * @date 2022/12/20
 */
public class SystemUserLoginDto {

    /**
     * 用户令牌
     * <p>
     * 登录成功返回
     * 用作于用户token, 每次请求将此数值放到请求头【Authorization】字段
     */
    private String userToken;

    public SystemUserLoginDto() {
    }

    public SystemUserLoginDto(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
