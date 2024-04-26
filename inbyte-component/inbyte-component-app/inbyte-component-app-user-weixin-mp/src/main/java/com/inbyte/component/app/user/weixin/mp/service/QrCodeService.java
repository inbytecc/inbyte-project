package com.inbyte.component.app.user.weixin.mp.service;

import com.inbyte.commons.model.dto.BasePath;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ScanEventNotify;
import com.inbyte.component.app.user.weixin.mp.model.qrcode.ShareDto;

/**
 * 分享服务
 *
 * @author chenjw
 * @date 2022-11-28 13:54:45
 **/
public interface QrCodeService {

    /**
     * 生成分享
     *
     * @param param
     * @return Result
     **/
    R<ShareDto> shareDirectly(BasePath param);

    /**
     * 创建分享二维码
     *
     * @param param
     * @return
     */
    R qrCode(BasePath param);

    /**
     * 访问事件
     *
     * @param scanEventNotify
     * @return
     */
    void viewed(ScanEventNotify scanEventNotify);

}