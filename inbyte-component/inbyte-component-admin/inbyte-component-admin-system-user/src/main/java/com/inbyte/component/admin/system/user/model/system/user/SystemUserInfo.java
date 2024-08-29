package com.inbyte.component.admin.system.user.model.system.user;

import java.util.List;

/**
 * 平台用户
 *
 * @author chenjw
 * @date 2023/1/28
 */
public class SystemUserInfo {

    /** 用户ID */
    private Integer userId;
    /** 昵称 */
    private String userName;

    /** 头像 */
    private String avatar;

    /** 需要修改密码 */
    private Integer needUpdatePwd;

    /** 角色 */
    private List<String> role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public Integer getNeedUpdatePwd() {
        return needUpdatePwd;
    }

    public void setNeedUpdatePwd(Integer needUpdatePwd) {
        this.needUpdatePwd = needUpdatePwd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
