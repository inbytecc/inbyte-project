package com.inbyte.component.common.weixin.enterprise.config;

import com.google.common.collect.Maps;
import me.chanjar.weixin.cp.api.WxCpGroupRobotService;
import me.chanjar.weixin.cp.api.impl.WxCpGroupRobotServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 企业微信群机器人工厂
 *
 * @author chenjw
 */
@Component
public class WxCpGroupRobotFactory {

    private Map<String, WxCpGroupRobotService> groupRobots = Maps.newHashMap();

    public WxCpGroupRobotService getGroupRobot(String robotWebHookKey) {
        WxCpGroupRobotService cpService = groupRobots.get(robotWebHookKey);
        if (cpService == null) {
            WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
            config.setWebhookKey(robotWebHookKey);

            WxCpServiceImpl wxCpService = new WxCpServiceImpl();
            wxCpService.setWxCpConfigStorage(config);
            WxCpGroupRobotService robot = new WxCpGroupRobotServiceImpl(wxCpService);
            groupRobots.put(robotWebHookKey, robot);
            return robot;
        }
        return cpService;
    }


}
