package com.inbyte.component.app.user.model;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * 地理位置更新
 *
 * 小程序接口地址：
 * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getLocation.html
 * @author chenjw
 */
public class LocationUpdate {

    /**
     * 纬度
     * 范围为-90~90, 负数表示南纬。使用 gcj02 国测局坐标系
     */
    @NotNull(message = "经度不能为空")
    private BigDecimal latitude;

    /**
     * 经度
     * 范围为-180~180, 负数表示西经。使用 gcj02 国测局坐标系
     */
    @NotNull(message = "纬度不能为空")
    private BigDecimal longitude;

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
