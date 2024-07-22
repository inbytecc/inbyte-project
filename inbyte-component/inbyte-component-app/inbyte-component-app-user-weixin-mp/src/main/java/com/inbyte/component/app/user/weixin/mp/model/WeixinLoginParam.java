package com.inbyte.component.app.user.weixin.mp.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 微信登录
 *
 * @author chenjw
 */
@Getter
@Setter
public class WeixinLoginParam {

    /**
     * 微信获取 OpenId js_code
     */
    @NotNull(message = "微信 js_code 不能为空")
    @NotBlank(message = "微信 js_code 不能为空")
    private String openIdJsCode;

    /**
     * 经度 Longitude
     * <p>
     * 优先获取高精度, 其次选择模糊地理位置
     * 小程序接口地址：
     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getLocation.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getFuzzyLocation.html
     * <p>
     * 注：获取定位时，除了调用 location/update 接口之外，还需要缓存本地，供注册接口使用
     * 因为未登录的用户无法调用 location/update 接口
     */
    private BigDecimal longitude;

    /**
     * 纬度 Latitude
     * <p>
     * 优先获取高精度, 其次选择模糊地理位置
     * 小程序接口地址：
     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getLocation.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getFuzzyLocation.html
     * <p>
     * 注：获取定位时，除了调用 location/update 接口之外，还需要缓存本地，供注册接口使用
     * 因为未登录的用户无法调用 location/update 接口
     */
    private BigDecimal latitude;

    /**
     * 外部用户ID 推荐人
     * 来自于直接分享场景scene中的seid, Share External User ID，也是常用的微信用户表主键eid
     */
    private Integer s;

    /**
     * 二维码ID
     * 来自于商户或用户生成的二维码scene中的 qcid qrCode Id
     * qcid和q，设置为q
     */
    private Integer q;

    /**
     * 二维码类型
     * 来自于商户或用户生成的二维码scene中的qctp qrCode Type
     * qctp和t，设置为t
     *
     * 空：自然流量
     * 0：小程序用户分享
     * 1：商家推广码
     */
    private Integer t;

}
