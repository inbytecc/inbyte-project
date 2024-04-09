package com.inbyte.component.app.user.model;

/**
 * 用户登录
 *
 * @author chenjw
 */
public class UserLoginDto {

    /**
     * 用户令牌
     * <p>
     * 登录成功返回
     * 用作于用户token, 每次请求将此数值放到请求头【Authorization】字段
     */
    private String userToken;

    /**
     * 已注册
     * <p>
     * 已注册能够访问订单，我的课程，等需要注册才能访问的模块
     */
    private Integer registered;

    public UserLoginDto() {
    }

    public UserLoginDto(String userToken, Integer registered) {
        this.userToken = userToken;
        this.registered = registered;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getRegistered() {
        return registered;
    }

    public void setRegistered(Integer registered) {
        this.registered = registered;
    }
}
