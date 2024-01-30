package com.inbyte.component.common.weixin.enterprise.client;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.weixin.enterprise.config.WxCpGroupRobotFactory;
import com.inbyte.component.common.weixin.enterprise.model.WxCpTextMessageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Component;

/**
 * 企业微信消息
 *
 * @author chenjw
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WxCpGroupRobotClient {

    private final WxCpGroupRobotFactory wxCpGroupRobotFactory;

    /**
     * 消息发送
     *
     * @return
     */
    public R sendText(WxCpTextMessageParam param) throws WxErrorException {
        String text = param.getText();
        if (text.length() > 2048 ){
            text = text.substring(2048);
        }
        wxCpGroupRobotFactory.getGroupRobot(param.getGroupRobotWebHookKey()).sendText(
                text, param.getMentionedList(), param.getMobileList());
        return R.success("发送成功");
    }

    /**
     * 消息发送
     *
     * @param groupRobotWebHookKey
     * @param text
     * @return
     */
    public R sendText(String groupRobotWebHookKey, String text) {
        if (text.length() > 3500){
            text = text.substring(0, 3500);
        }
        try {
            wxCpGroupRobotFactory.getGroupRobot(groupRobotWebHookKey).sendText(
                    text, null, null);
        } catch (WxErrorException e) {
            log.error("企微消息发送失败", e);
            return R.failure("企微消息发送失败");
        }
        return R.success("发送成功");
    }

}
