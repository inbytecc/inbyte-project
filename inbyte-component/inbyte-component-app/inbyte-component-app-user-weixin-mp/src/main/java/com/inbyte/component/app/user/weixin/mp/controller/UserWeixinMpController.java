package com.inbyte.component.app.user.weixin.mp.controller;

import com.inbyte.component.app.user.model.LocationUpdate;
import com.inbyte.component.app.user.weixin.mp.model.*;
import com.inbyte.component.app.user.weixin.mp.service.UserWeixinMpService;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户微信
 *
 * @author chenjw
 * @date 2023-09-15 16:55:46
 **/
@RestController
@RequestMapping("user/weixin/mp")
public class UserWeixinMpController {

    @Autowired
    private UserWeixinMpService userWeixinMpService;

    /**
     * 微信登录
     * <p>
     * 微信小程序 openId 静默登录
     * 登录后注册 user_weixin, 但不注册平台账号, 仅限未绑定手机号接口访问
     * <p>
     * 微信接口文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html
     *
     * 用户邀请 && 二维码分享逻辑
     * eid etp 小程序直接分享时有此参数
     * qcid qctp 参数来自于小程序二维码, 其中分为商家和用户生成
     **/
    @PostMapping("login")
    public R<UserLoginDto> login(@RequestBody @Valid WeixinLoginParam param) {
        return userWeixinMpService.login(param);
    }

    /**
     * 微信注册
     * <p>
     * 微信小程序注册账号<br/>
     * 小程序端先调用获取手机号接口, 获得js_code<br/>
     * 微信接口文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
     **/
    @PostMapping("register")
    public R<UserLoginDto> register(@RequestBody @Valid UserWeixinRegisterParam param) {
        return userWeixinMpService.register(param);
    }

    /**
     * 更新信息
     * <p>
     **/
    @PostMapping("update")
    public R<String> update(@RequestBody UserWeixinMpUpdate userWeixinMpUpdate) {
        return userWeixinMpService.update(userWeixinMpUpdate);
    }

    /**
     * 个人信息
     **/
    @GetMapping("info")
    public R<UserWeixinDto> info() {
        return userWeixinMpService.info();
    }

    /**
     * 更新地理位置
     *
     * @return
     */
    @PostMapping("location")
    public R updateLocation(@RequestBody @Valid LocationUpdate locationUpdate) {
        return userWeixinMpService.updateLocation(locationUpdate);
    }
}
