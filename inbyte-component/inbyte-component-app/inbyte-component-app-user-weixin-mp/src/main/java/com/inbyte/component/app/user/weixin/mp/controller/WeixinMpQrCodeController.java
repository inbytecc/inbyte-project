package com.inbyte.component.app.user.weixin.mp.controller;

import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.sign.framework.AppUtil;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ShareDto;
import com.inbyte.component.app.user.weixin.mp.service.QrCodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@RequestMapping
@RestController
public class WeixinMpQrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 获取微信小程序二维码
     * <p>
     * <br/><br/>
     * 请求成功, 返回的图片 Buffer<br/>
     * 请求失败, 返回标注 JSON 数据实体, 状态 400, 提示 msg 错误信息<br/>
     * 微信官方获取微信小程序二维码接口文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/qr-code/getUnlimitedQRCode.html
     * <p>
     * 为方便规范参数 JSONObject 传输, 故使用 POST 请求
     *
     * @param param
     * @return
     **/
    @PostMapping({"qr-code/weixin/mini-program/image-data", "qr-code/weixin/mp/image-data"})
    public R qrCode(@RequestBody @Valid BasePath param) {
        return R.ok(qrCodeService.qrCode(param));
    }


    /**
     * 直接分享
     * <p>
     * 直接分享微信好友<br/><br/>
     * 分享前调用此接口
     * @param param
     * @return Result<List < ShareBrief>>
     **/
    @PostMapping("weixin/mini-program/share-directly")
    public R<ShareDto> create(@Valid @RequestBody BasePath param) {
        return qrCodeService.shareDirectly(param);
    }

    /**
     * 二维码扫码事件通知
     *
     * <p>
     * 扫码小程序二维码, 本地保存 qrCodeId, qrCodeType<br/>
     * 同时调用此接口, 服务端记录二维码访问情况<br/>
     *
     * @return Result
     **/
    @PostMapping("qr-code/weixin/mini-program/scan-notify")
    public R scanCodeEventNotify(@RequestBody ScanEventNotify scanEventNotify) {
        scanEventNotify.setEid(SessionUtil.getEid());
        scanEventNotify.setEtp(AppUtil.getAppType());
        qrCodeService.viewed(scanEventNotify);
        return R.ok();
    }

}