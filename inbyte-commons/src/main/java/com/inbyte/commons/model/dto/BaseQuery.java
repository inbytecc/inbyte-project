package com.inbyte.commons.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 请求参数基类
 *
 * @author chenjw
 */
public class BaseQuery {

    /**
     * 用户Id
     * 服务端参数，前端忽略
     * @ignore
     */
    @JsonIgnore
    private Integer userId;
    /**
     * 当前场馆ID
     * 服务端参数，前端忽略
     * @ignore
     */
    private String venueId;
    /**
     * 当前场馆ID
     * 服务端参数，前端忽略
     * @ignore
     */
    @JsonIgnore
    private String mctNo;

    public String getMctNo() {
        return mctNo;
    }

    public void setMctNo(String mctNo) {
        this.mctNo = mctNo;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
