package com.inbyte.component.admin.marketing.model.qrcode;

import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 二维码生成参数
 *
 * @author chenjw
 * @date 2023-7-25
 */
@Getter
@Setter
public class QrCodeDownloadParam {

    /**
     * 二维码ID
     */
    @NotNull
    private Integer qcid;
    /**
     * env_version	string	否	要打开的小程序版本。正式版为 "release"，体验版为 "trial"，开发版为 "develop"。默认是正式版。
     */
    private String envVersion;
    /**
     * width	number	否	默认430，二维码的宽度，单位 px，最小 280px，最大 1280px
     */
    private int width;
    /**
     * auto_color	bool	否	自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
     */
    private Integer autoColor;
    /**
     * line_color	object	否	默认是{"r":0,"g":0,"b":0} 。auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     */
    private WxMaCodeLineColor lineColor;
    /**
     * is_hyaline	bool	否	默认是false，是否需要透明底色，为 true 时，生成透明底色的小程序
     */
    private Integer isHyaline;
}
