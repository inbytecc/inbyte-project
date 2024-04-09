package com.inbyte.component.common.weixin.robot;

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
     * 微信群
     */
    private String roomName;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}