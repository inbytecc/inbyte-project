package com.inbyte.component.common.weixin.enterprise.config;

import com.google.common.collect.Maps;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.common.weixin.enterprise.dao.InbyteNoticeEnterpriseWeixinMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WxCpGroupRobotFactory {

    private Map<String, WxCpGroupRobotService> groupRobots = Maps.newHashMap();
    /**
     * 故障报警群
     * 易思助手
     */
    public static final String WEIXIN_ERROR_GROUP_BOT_KEY = "d189749d-e656-4442-bd5b-08e7ea78bbd3";
    private final InbyteNoticeEnterpriseWeixinMapper inbyteNoticeEnterpriseWeixinMapper;

    public WxCpGroupRobotService getGroupRobot(String venueId) {
        String robotId = getRobotId(venueId);
        WxCpGroupRobotService cpService = groupRobots.get(robotId);
        if (cpService == null) {
            WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
            config.setWebhookKey(robotId);

            WxCpServiceImpl wxCpService = new WxCpServiceImpl();
            wxCpService.setWxCpConfigStorage(config);
            WxCpGroupRobotService robot = new WxCpGroupRobotServiceImpl(wxCpService);
            groupRobots.put(robotId, robot);
            return robot;
        }
        return cpService;
    }

    private String getRobotId(String venueId) {
        String robot = inbyteNoticeEnterpriseWeixinMapper.getRobot(venueId);
        if (StringUtil.isEmpty(robot)) {
            return WEIXIN_ERROR_GROUP_BOT_KEY;
        }
        return robot;
    }


    public WxCpGroupRobotService getDefaultGroupRobot() {
        String robotId = WEIXIN_ERROR_GROUP_BOT_KEY;
        WxCpGroupRobotService cpService = groupRobots.get(robotId);
        if (cpService == null) {
            WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
            config.setWebhookKey(robotId);

            WxCpServiceImpl wxCpService = new WxCpServiceImpl();
            wxCpService.setWxCpConfigStorage(config);
            WxCpGroupRobotService robot = new WxCpGroupRobotServiceImpl(wxCpService);
            groupRobots.put(robotId, robot);
            return robot;
        }
        return cpService;
    }
}
