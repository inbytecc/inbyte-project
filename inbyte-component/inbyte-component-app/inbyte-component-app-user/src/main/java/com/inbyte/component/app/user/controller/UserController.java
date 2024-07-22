package com.inbyte.component.app.user.controller;

import com.inbyte.commons.model.RateLimit;
import com.inbyte.component.app.user.ComponentUserProperties;
import com.inbyte.component.app.user.model.*;
import com.inbyte.component.app.user.service.UserService;
import com.inbyte.commons.model.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author chenjw
 * @date 2016年4月1日 下午3:19:10
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ComponentUserProperties componentUserProperties;

    /**
     * 邮箱登录
     * <p>
     * 使用JWT无状态Token, 支持多设备登录, 但不支持服务端控制退出等操作
     **/
    @RateLimit(rate = 1, unit = TimeUnit.SECONDS)
    @PostMapping("login/email")
    public R<UserLoginDto> emailLogin(@RequestBody @Valid UserEmailLoginParam param) {
        return userService.emailLogin(param);
    }

    /**
     * 账号注册
     * <p>
     * 使用JWT无状态Token, 支持多设备登录, 但不支持服务端控制退出等操作
     **/
    @PostMapping("register")
    public R<UserLoginDto> register(@RequestBody @Valid UserRegisterParam param) {
        if (!componentUserProperties.isAllowRegisterNotVerified()) {
            return R.failure("请使用验证码注册方法");
        }
        return userService.register(param);
    }

    /**
     * 邮箱账号注册
     * <p>
     * 使用JWT无状态Token, 支持多设备登录, 但不支持服务端控制退出等操作
     **/
    @PostMapping("register/with-email")
    public R<UserLoginDto> registerWithEmail(@RequestBody @Valid UserRegisterWithVerifyCodeParam param) {
        return userService.registerWithEmail(param);
    }

    /**
     * 获取邮箱注册验证码
     * <p>
     * 使用JWT无状态Token, 支持多设备登录, 但不支持服务端控制退出等操作
     **/
    @RateLimit(rate = 1, unit = TimeUnit.MINUTES)
    @GetMapping("register/email/{email}/verify-code")
    public R<UserLoginDto> getRegisterEmailVerifyCode(@PathVariable String email) {
        return userService.getRegisterEmailVerifyCode(email);
    }

    /**
     * 手机号登录
     * <p>
     * 使用JWT无状态Token, 支持多设备登录, 但不支持服务端控制退出等操作
     **/
    @PostMapping("login/tel")
    public R<UserLoginDto> telLogin(@RequestBody @Valid UserTelLoginParam param) {
        return userService.telLogin(param);
    }

    /**
     * 退出登录
     * <p>
     * 调用此接口后, 清除本地 userToken
     **/
    @PostMapping("logout")
    public R<String> logout() {
        return R.ok("退出成功");
    }


    /**
     * 个人基本信息
     **/
    @GetMapping("info")
    public R<UserBrief> info() {
        return userService.info();
    }

    /**
     * 获取邮箱忘记密码验证码
     * <p>
     * 通过邮箱找回
     **/
    @RateLimit(rate = 1, unit = TimeUnit.MINUTES)
    @GetMapping("forget-pwd/email/{email}/verify-code")
    public R<UserLoginDto> emailForgetPwd(@PathVariable String email) {
        return userService.emailForgetPwd(email);
    }

    /**
     * 设置邮箱密码
     **/
    @PostMapping("reset-pwd/email")
    public R<UserLoginDto> emailResetPwd(@RequestBody @Valid EmailResetPwdParam emailResetPwdParam) {
        return userService.emailResetPwd(emailResetPwdParam);
    }
}
