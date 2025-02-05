package com.inbyte.util.weixin.mp.open.client;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import com.inbyte.util.weixin.mp.client.WxMsgClient;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
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
public class WxMsgOpenClient implements WxMsgClient {

    @Autowired
    private WxOpenService wxOpenService;

    @Override
    public void sendSubscribeMsg(String appId, String toUser, String templateId, String page, List<WxMaSubscribeMessage.MsgData> data) throws WxErrorException {
        WxMaService wxMaService = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId);

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
        WxMaService wxMaService = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId);

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