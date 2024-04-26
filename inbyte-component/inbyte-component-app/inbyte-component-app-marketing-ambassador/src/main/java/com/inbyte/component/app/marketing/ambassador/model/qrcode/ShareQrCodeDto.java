package com.inbyte.component.app.marketing.ambassador.model.qrcode;

import lombok.Getter;
import lombok.Setter;

/**
 * 分享摘要
 *
 * @author chenjw
 * @date 2022-11-28 14:42:12
 **/
@Getter
@Setter
public class ShareQrCodeDto {

    /**
     * 背景图片
     */
    private String bgImage;
    /**
     * 二维码 Base64
     */
    private String qrCode;

}