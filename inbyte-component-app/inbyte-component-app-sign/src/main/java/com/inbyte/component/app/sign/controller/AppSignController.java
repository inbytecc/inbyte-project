package com.inbyte.component.app.sign.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.sign.model.AppSignDto;
import com.inbyte.component.app.sign.model.AppSignParam;
import com.inbyte.component.app.sign.service.AppSignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 应用服务
 *
 * @author chenjw
 * @date 2022年12月31日 下午3:19:10
 */
@RestController
@RequestMapping
public class AppSignController {

    @Autowired
    private AppSignService appSignService;

    /**
     * app签名
     * <p>
     * 背景：设计目标本服务端适用于不同小程序，不同商家访问
     * 目的：为了区分商家微信支付，公众号，用户，需要 App-Token 来识别
     * 处理办法：首次打开小程序时，调用此接口，获得 App-Token 签名，缓存在本地，在之后的所有请求都带上此内容
     * 如果本地已有 App-Token，则无需重新请求
     **/
    @PostMapping("app/sign")
    public R<AppSignDto> appSign(@RequestBody @Valid AppSignParam param) {
        return appSignService.appSign(param);
    }

}
