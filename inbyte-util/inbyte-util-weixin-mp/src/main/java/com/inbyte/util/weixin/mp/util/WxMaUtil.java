package com.inbyte.util.weixin.mp.util;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnBean(WxMaService.class)
public class WxMaUtil {
    private final WxMaService wxMaService;

    public WxMaUtil(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    /**
     * 获取微信小程序用户session信息
     *
     * @param code 登录时获取的code
     * @return session信息
     */
    public WxMaJscode2SessionResult getSessionInfo(String code) {
        try {
            return wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            log.error("获取微信小程序用户session信息失败", e);
            throw new RuntimeException("获取微信小程序用户session信息失败", e);
        }
    }

    /**
     * 获取小程序码（永久有效、数量暂无限制）
     *
     * @param scene     最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~
     * @param page      必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index,根路径前不要填加/,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
     * @return 小程序码图片字节数组
     */
    public byte[] getUnlimitedQrCode(String scene, String page) {
        try {
            return wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(scene, page, false, null, 430, false, null, false);
        } catch (WxErrorException e) {
            log.error("获取小程序码失败", e);
            throw new RuntimeException("获取小程序码失败", e);
        }
    }
} 