package com.inbyte.component.common.weixin.robot;

import com.google.common.base.Throwables;
import com.inbyte.commons.api.SystemAlarm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 微信机器人报警实现
 *
 * @author chenjw
 */
@Slf4j
public class SystemAlertWeixinRobot implements SystemAlarm {

    @Autowired
    private WeixinRobotClient weixinRobotClient;
    @Autowired
    private WeixinRobotProperties weixinRobotProperties;


    @Override
    public void alert(String module, String requestInfo) {
        weixinRobotClient.sendRoomMsg(module, weixinRobotProperties.getRoomName(), requestInfo);
    }

    @Override
    public void alert(String module, String requestInfo, Exception e) {
        Throwables.getStackTraceAsString(e);
        weixinRobotClient.sendRoomMsg(module, weixinRobotProperties.getRoomName(), requestInfo);
    }
}
