package com.inbyte.util.weixin.mp.client;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;

/**
 * 微信用户统一接口
 */
public interface WxUserClient {
    /**
     * 获取用户session信息
     *
     * @param code 登录时获取的code
     * @return 用户session信息，包含openId、unionId等
     */
    WxMaJscode2SessionResult getSessionInfo(String appId, String code);

    /**
     * 获取用户手机号
     *
     * @param code 手机号获取凭证
     * @return 用户手机号信息
     */
    WxMaPhoneNumberInfo getPhoneInfo(String appId, String code);
}
