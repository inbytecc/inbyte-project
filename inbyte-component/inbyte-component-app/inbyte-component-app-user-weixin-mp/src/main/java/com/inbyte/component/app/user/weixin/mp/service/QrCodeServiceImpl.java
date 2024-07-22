package com.inbyte.component.app.user.weixin.mp.service;

import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.SpringContextUtil;
import com.inbyte.component.app.sign.framework.AppUtil;
import com.inbyte.component.app.user.dict.UserSourceTypeDict;
import com.inbyte.component.app.user.event.MerchantQrcodeViewedEvent;
import com.inbyte.component.app.user.event.UserQrcodeViewedEvent;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.weixin.mp.SceneUtil;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ShareDto;
import com.inbyte.util.weixin.mp.client.WxMpQrCodeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分享服务
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
@Slf4j
@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    private WxMpQrCodeClient wxMpQrCodeClient;

    /**
     * 直接分享
     *
     * @param param
     * @return
     */
    @Override
    public R<ShareDto> shareDirectly(BasePath param) {
        ShareDto shareDto = new ShareDto();
        shareDto.setScene(SceneUtil.getUserShareScene(SessionUtil.getEid(), param.getPathParam()));
        return R.ok("创建成功", shareDto);
    }


    /**
     * 获取小程序码, 适用于需要的码数量极多的业务场景。
     * 通过该接口生成的小程序码, 永久有效, 数量暂无限制。 更多用法详见 获取二维码。
     * 接口文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/qr-code/getUnlimitedQRCode.html
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R qrCode(BasePath param) {
        String scene = SceneUtil.getUserShareScene(SessionUtil.getEid(), param.getPathParam());
        return wxMpQrCodeClient.qrCodeBase64(AppUtil.getAppId(), param.getPath(), scene, 430);
    }

    @Async
    @Override
    public void viewed(ScanEventNotify scanEventNotify) {
        // 用户分享二维码
        if (scanEventNotify.getT() == UserSourceTypeDict.User_Share.code) {
            UserQrcodeViewedEvent userQrcodeViewedEvent = new UserQrcodeViewedEvent(this,
                    scanEventNotify.getShareEid(), SessionUtil.getEid(), SessionUtil.getSessionUser().getAppType());
            SpringContextUtil.getContext().publishEvent(userQrcodeViewedEvent);
        } else {
            // 商家分享二维码
            MerchantQrcodeViewedEvent userQrcodeViewedEvent = new MerchantQrcodeViewedEvent(this,
                    scanEventNotify.getQ(), SessionUtil.getEid(), SessionUtil.getSessionUser().getAppType());
            SpringContextUtil.getContext().publishEvent(userQrcodeViewedEvent);
        }
    }

}