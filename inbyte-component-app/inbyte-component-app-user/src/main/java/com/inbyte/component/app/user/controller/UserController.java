package com.inbyte.component.app.user.controller;

import com.inbyte.component.app.user.model.UserLoginDto;
import com.inbyte.component.app.user.model.UserTelLoginParam;
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

//    @GetMapping("t")
//    public void test() {
//        userService.getRandomCommonAvatar();
//    }
}
