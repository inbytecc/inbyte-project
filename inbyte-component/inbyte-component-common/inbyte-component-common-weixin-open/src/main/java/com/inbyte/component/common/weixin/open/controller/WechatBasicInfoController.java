package com.inbyte.component.common.weixin.open.controller;

import com.inbyte.component.common.weixin.open.service.WxOpenPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代商家管理小程序 - 基础信息管理
 *
 * @author chenjw
 */
@Slf4j
@RestController
@RequestMapping("wx-open-platform/basic-info")
public class WechatBasicInfoController {
    @Autowired
    protected WxOpenPlatformService wxOpenService;

    /**
     * 小程序名称检测
     *
     * @return
     */
    @RequestMapping("/check-nickname")
    public Object receiveTicket(@RequestParam("nickname") String nickname) {
//        wxOpenService.
        return "success";
    }

}
