package com.inbyte.util.weixin.mp.open.client;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.inbyte.util.weixin.mp.client.WxUserClient;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WxUserOpenClient implements WxUserClient {
    private final WxOpenService wxOpenService;

    public WxUserOpenClient(WxOpenService wxOpenService) {
        this.wxOpenService = wxOpenService;
    }

    @Override
    public WxMaJscode2SessionResult getSessionInfo(String appId, String code) {
        try {
            // 使用服务商模式获取session信息
            return wxOpenService.getWxOpenComponentService()
                    .miniappJscode2Session(appId, code);
        } catch (WxErrorException e) {
            log.error("获取微信开放平台用户session信息失败", e);
            throw new RuntimeException("获取微信开放平台用户session信息失败", e);
        }
    }

    @Override
    public WxMaPhoneNumberInfo getPhoneInfo(String appId, String code) {
        try {
            // 使用服务商模式获取手机号信息
            return wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getUserService().getPhoneNoInfo(code);
        } catch (WxErrorException e) {
            log.error("获取微信开放平台用户手机号失败", e);
            throw new RuntimeException("获取微信开放平台用户手机号失败", e);
        }
    }
} 