package com.inbyte.component.common.weixin.enterprise.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.weixin.enterprise.config.WxCpGroupRobotFactory;
import com.inbyte.component.common.weixin.enterprise.model.WxCpTextMessageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 企业微信消息
 *
 * @author chenjw
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QywxGroupRobotService {

    private static final int MAX_LENGTH = 4000;
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

        wxCpGroupRobotFactory.getGroupRobot(param.getVenueId()).sendText(
                text, param.getMentionedList(), param.getMobileList());
        return R.ok("发送成功");
    }

    /**
     * 消息发送
     *
     * @param venueId
     * @param content
     * @return
     */
    public R sendText(String venueId, String content) {
        if (content.length() > MAX_LENGTH){
            content = content.substring(0, MAX_LENGTH);
        }
        try {
            wxCpGroupRobotFactory.getGroupRobot(venueId).sendText(
                    content, null, null);
        } catch (WxErrorException e) {
            log.error("企微消息发送失败", e);
            return R.failure("企微消息发送失败");
        }
        return R.ok("发送成功");
    }

    /**
     * Markdown消息发送
     *
     * @param venueId
     * @param text
     * @return
     */
    @Async
    public void sendMarkDown(String venueId, String text) {
        try {
            wxCpGroupRobotFactory.getGroupRobot(venueId).sendText(
                    text, null, null);
        } catch (WxErrorException e) {
            log.error("企微消息发送失败", e);
        }
    }

    public void sendTextDefault(String content) {
        if (content.length() > MAX_LENGTH){
            content = content.substring(0, MAX_LENGTH);
        }
        try {
            wxCpGroupRobotFactory.getDefaultGroupRobot().sendText(
                    content, null, null);
        } catch (WxErrorException e) {
            log.error("企微消息发送失败", e);
        }
    }

}
