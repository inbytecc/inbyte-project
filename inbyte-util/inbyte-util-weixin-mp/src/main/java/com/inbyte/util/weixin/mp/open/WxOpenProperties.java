package com.inbyte.util.weixin.mp.open;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.open")
public class WxOpenProperties {

    /**
     * 设置微信三方平台的appid
     */
    private String componentAppId;

    /**
     * 设置微信三方平台的app secret
     */
    private String componentSecret;

    /**
     * 设置微信三方平台的token
     */
    private String componentToken;

    /**
     * 设置微信三方平台的EncodingAESKey
     */
    private String componentAesKey;

} 