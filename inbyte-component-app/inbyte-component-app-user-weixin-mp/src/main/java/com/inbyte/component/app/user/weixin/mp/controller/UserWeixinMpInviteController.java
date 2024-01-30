package com.inbyte.component.app.user.weixin.mp.controller;

import com.inbyte.commons.model.dto.BasePage;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.weixin.mp.model.UserInviteDto;
import com.inbyte.component.app.user.weixin.mp.service.UserWeixinMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户微信邀请
 *
 * @author chenjw
 * @date 2023年1月1日 凌晨1点5分
 */
@RestController
@RequestMapping("user/weixin/mp/invite")
public class UserWeixinMpInviteController {

    @Autowired
    private UserWeixinMpService userWeixinMpService;

    /**
     * 邀请总人数
     * <p>
     **/
    @GetMapping("count")
    public R count() {
        return userWeixinMpService.count();
    }

    /**
     * 邀请的用户
     * <p>
     **/
    @GetMapping
    public R<List<UserInviteDto>> list(@ModelAttribute BasePage basePage) {
        return userWeixinMpService.list(basePage);
    }

}
