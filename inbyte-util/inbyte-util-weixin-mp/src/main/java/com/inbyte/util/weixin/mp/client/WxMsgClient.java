package com.inbyte.util.weixin.mp.client;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.List;

/**
 * 微信消息推送接口
 *
 * @author chenjw
 */
public interface WxMsgClient {

    /**
     * 发送订阅消息
     *
     * @param toUser     接收者openid
     * @param templateId 模板ID
     * @param page       点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     * @param data       模板内容，不填则下发空模板
     * @throws WxErrorException 异常
     */
    void sendSubscribeMsg(String appId, String toUser, String templateId, String page, List<WxMaSubscribeMessage.MsgData> data) throws WxErrorException;

    /**
     * 发送统一服务消息
     *
     * @param toUser      用户openid
     * @param templateId  模板ID
     * @param url         点击消息跳转的链接
     * @param miniProgram 跳小程序所需数据，不需跳小程序可不用传该数据
     * @param data        模板内容
     * @throws WxErrorException 异常
     */
    void sendUniformMsg(String appId, String toUser, String templateId, String url, WxMaUniformMessage.MiniProgram miniProgram, List<WxMaTemplateData> data) throws WxErrorException;

} 