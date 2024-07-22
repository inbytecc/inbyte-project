package com.inbyte.component.app.user.weixin.mp.controller;

import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.weixin.mp.service.WeixinMpLinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序链接
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@RequestMapping("mp/weixin")
@RestController
public class WeixinMpLinkController {

    @Autowired
    private WeixinMpLinkService weixinMpLinkService;


    /**
     * Url 长链接
     * @param basePath
     * @return
     */
    @PostMapping("url-link")
    public R<String> getUrlLink(@Valid @RequestBody BasePath basePath) {
        return weixinMpLinkService.getUrlLink(basePath);
    }

    /**
     * 小程序短链接
     * @param basePath
     * @return
     */
    @PostMapping("short-link")
    public R<String> getShortLink(@Valid @RequestBody BasePath basePath) {
        return weixinMpLinkService.getShortLink(basePath);
    }

}