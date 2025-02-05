package com.inbyte.util.weixin.mp.util;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnBean(WxOpenService.class)
public class WxOpenUtil {
    private final WxOpenService wxOpenService;

    public WxOpenUtil(WxOpenService wxOpenService) {
        this.wxOpenService = wxOpenService;
    }

    /**
     * 使用授权码获取授权信息
     *
     * @param authorizationCode 授权码
     * @return 授权信息
     */
    public WxOpenQueryAuthResult getQueryAuth(String authorizationCode) {
        try {
            return wxOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
        } catch (WxErrorException e) {
            log.error("获取授权信息失败", e);
            throw new RuntimeException("获取授权信息失败", e);
        }
    }

    /**
     * 获取授权方的帐号基本信息
     *
     * @param authorizerAppId 授权方appid
     * @return 授权方的帐号基本信息
     */
    public WxOpenAuthorizerInfoResult getAuthorizerInfo(String authorizerAppId) {
        try {
            return wxOpenService.getWxOpenComponentService().getAuthorizerInfo(authorizerAppId);
        } catch (WxErrorException e) {
            log.error("获取授权方的帐号基本信息失败", e);
            throw new RuntimeException("获取授权方的帐号基本信息失败", e);
        }
    }
} 