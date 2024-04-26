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
public class ShareDto {

    /**
     * 场景
     * 用于 wxacode.getUnlimited 接口请求参数【scene】
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html
     */
    private String scene;

}