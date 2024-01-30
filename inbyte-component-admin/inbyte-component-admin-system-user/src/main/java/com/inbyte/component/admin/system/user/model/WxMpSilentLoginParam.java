package com.inbyte.component.admin.system.user.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信登录
 *
 * @author chenjw
 */
@Getter
@Setter
public class WxMpSilentLoginParam {

    /**
     * 微信获取 OpenId js_code
     */
    @NotNull(message = "微信 js_code 不能为空")
    @NotBlank(message = "微信 js_code 不能为空")
    private String openIdJsCode;

//    /**
//     * 经度 Longitude
//     * <p>
//     * 优先获取高精度, 其次选择模糊地理位置
//     * 小程序接口地址：
//     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getLocation.html
//     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getFuzzyLocation.html
//     * <p>
//     * 注：获取定位时，除了调用 location/update 接口之外，还需要缓存本地，供注册接口使用
//     * 因为未登录的用户无法调用 location/update 接口
//     */
//    private BigDecimal longitude;
//
//    /**
//     * 纬度 Latitude
//     * <p>
//     * 优先获取高精度, 其次选择模糊地理位置
//     * 小程序接口地址：
//     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getLocation.html
//     * https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.getFuzzyLocation.html
//     * <p>
//     * 注：获取定位时，除了调用 location/update 接口之外，还需要缓存本地，供注册接口使用
//     * 因为未登录的用户无法调用 location/update 接口
//     */
//    private BigDecimal latitude;

}
