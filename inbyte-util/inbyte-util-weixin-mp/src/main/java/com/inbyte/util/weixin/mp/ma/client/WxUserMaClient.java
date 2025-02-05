package com.inbyte.util.weixin.mp.ma.client;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.exception.BizException;
import com.inbyte.util.weixin.mp.client.WxUserClient;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Service
public class WxUserMaClient implements WxUserClient {
    private final WxMaService wxMaService;

    public WxUserMaClient(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    /**
     * 静默登录
     */
    public WxMaJscode2SessionResult getSessionInfo(String appId, String jsCode) {
        if (StringUtils.isBlank(jsCode)) {
            throw BizException.failure("jsCode 不能为空");
        }

        if (!wxMaService.switchover(appId)) {
            log.error("微信登录, 未找到对应appId={}, 的配置, 请核实！", appId);
            throw BizException.failure("服务错误, 稍等一下马上就好");
        }

        try {
            log.info("微信用户静默登录, jsCode:{}", jsCode);
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(jsCode);
            log.info("微信用户静默登录, 返回结果:{}", JSON.toJSONString(result));
            return result;
        } catch (WxErrorException e) {
            log.error("微信登录异常", e);
            throw BizException.failure("微信登录异常, 稍等一下马上就好");
        } finally {
            //清理ThreadLocal
            WxMaConfigHolder.remove();
        }
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    public WxMaPhoneNumberInfo getPhoneInfo(@PathVariable("appId") String appId,
                                            @RequestParam("jsCode") String jsCode) {
        if (!wxMaService.switchover(appId)) {
            log.error("微信获取手机号, 未找到对应appId={}, 的配置, 请核实！", appId);
            throw BizException.failure("服务错误, 稍等一下马上就好");
        }

        try {
            return wxMaService.getUserService().getPhoneNoInfo(jsCode);
        } catch (WxErrorException e) {
            log.error("获取微信小程序用户手机号失败:{}", e);
            throw BizException.error("获取微信小程序用户手机号失败");
        } finally {
            WxMaConfigHolder.remove();
        }


    }
} 