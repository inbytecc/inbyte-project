package com.inbyte.component.app.user.controller;

import com.inbyte.component.app.user.model.*;
import com.inbyte.component.app.user.service.UserService;
import com.inbyte.commons.model.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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

    /**
     * 邮箱登录
     * <p>
     * 使用JWT无状态Token, 支持多设备登录, 但不支持服务端控制退出等操作
     **/
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
        return userService.register(param);
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
     * 忘记密码 - 邮箱
     * <p>
     * 通过邮箱找回
     **/
    @GetMapping("forget-pwd/email/{email}")
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
