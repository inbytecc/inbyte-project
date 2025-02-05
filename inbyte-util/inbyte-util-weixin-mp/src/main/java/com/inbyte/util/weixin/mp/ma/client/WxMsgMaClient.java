package com.inbyte.util.weixin.mp.ma.client;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import com.inbyte.util.weixin.mp.client.WxMsgClient;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 微信消息推送接口
 *
 * @author chenjw
 */
@Slf4j
@Component
public class WxMsgMaClient implements WxMsgClient {

    @Autowired
    private WxMaService wxMaService;

    @Override
    public void sendSubscribeMsg(String appId, String toUser, String templateId, String page, List<WxMaSubscribeMessage.MsgData> data) throws WxErrorException {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }

        WxMaSubscribeMessage subscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(toUser)
                .templateId(templateId)
                .page(page)
                .data(data)
                .build();
        wxMaService.getMsgService().sendSubscribeMsg(subscribeMessage);
    }

    @Override
    public void sendUniformMsg(String appId, String toUser, String templateId, String url, WxMaUniformMessage.MiniProgram miniProgram, List<WxMaTemplateData> data) throws WxErrorException {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appId));
        }

        WxMaUniformMessage uniformMessage = WxMaUniformMessage.builder()
                .toUser(toUser)
                .templateId(templateId)
                .url(url)
                .miniProgram(miniProgram)
                .data(data)
                .build();
        wxMaService.getMsgService().sendUniformMsg(uniformMessage);
    }
} 