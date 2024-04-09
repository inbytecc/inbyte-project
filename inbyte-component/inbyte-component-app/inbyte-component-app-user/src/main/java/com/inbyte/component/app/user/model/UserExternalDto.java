package com.inbyte.component.app.user.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * 外部用户
 *
 * @author chenjw
 */
public class UserExternalDto {

    /** 用户ID
     * */
    private Integer userId;

    /**
     * 外部用户ID
     */
    @NotNull
    private Integer eid;

    /**
     * 外部用户ID
     */
    @NotNull
    private Integer recommendEid;

    /** 昵称 */
    @NotNull
    private String nickName;

    /** 头像 */
    @NotNull
    private String avatar;

    /**
     * 最近经度
     */
    private BigDecimal longitude;

    /**
     * 最近纬度
     */
    private BigDecimal latitude;

    /**
     * 推荐注册类型
     */
    private Integer registerType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getRecommendEid() {
        return recommendEid;
    }

    public void setRecommendEid(Integer recommendEid) {
        this.recommendEid = recommendEid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getavatar() {
        return avatar;
    }

    public void setavatar(String avatar) {
        this.avatar = avatar;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }
}
