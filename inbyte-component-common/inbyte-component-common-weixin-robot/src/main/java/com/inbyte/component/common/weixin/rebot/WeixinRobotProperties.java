package com.inbyte.component.common.weixin.rebot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "inbyte.component.weixin.robot")
public class WeixinRobotProperties {

    /**
     * 访问秘钥
     */
    private String accessKey;
    /**
     * 服务器地址
     */
    private String serverUrl;

}