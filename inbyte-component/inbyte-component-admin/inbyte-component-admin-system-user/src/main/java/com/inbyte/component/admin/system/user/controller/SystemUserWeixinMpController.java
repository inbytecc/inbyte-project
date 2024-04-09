package com.inbyte.component.admin.system.user.controller;

import com.inbyte.component.admin.system.user.model.WxMpRegisterParam;
import com.inbyte.component.admin.system.user.model.WxMpSilentLoginParam;
import com.inbyte.component.admin.system.user.model.system.user.SystemUserLoginDto;
import com.inbyte.component.admin.system.user.service.SystemUserWeixinMpService;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商户微信用户
 *
 * @author chenjw
 * @date 2022-11-01 10:22:12
 **/
@RestController
@RequestMapping( "user/merchant/weixin/mp")
public class SystemUserWeixinMpController {

    @Autowired
    private SystemUserWeixinMpService systemUserWeixinMpService;

    /**
     * 微信登录
     * <p>
     * 微信小程序 openId 静默登录
     * 如果未绑定用户，返回4010，提示拉起获取手机号窗口
     * 同时返回的【userToken】作为下次接口请求头【Authorization】使用
     * <p>
     * 微信接口文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html
     *
     **/
    @PostMapping("login")
    public R<SystemUserLoginDto> weixinLogin(@RequestBody @Valid WxMpSilentLoginParam param) {
        return systemUserWeixinMpService.weiXinLogin(param);
    }

    /**
     * 绑定账号
     * <p>
     * 微信小程序绑定后台账号<br/>
     * 小程序端先调用获取手机号接口, 获得js_code<br/>
     * 如果手机号系统存在，则绑定处理。否则提示”联系管理员注册账号“
     *
     * 微信接口文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
     **/
    @PostMapping("bind")
    public R<SystemUserLoginDto> weixinRegister(@RequestBody @Valid WxMpRegisterParam param) {
        return systemUserWeixinMpService.weixinRegister(param);
    }

}
