package com.inbyte.util.weixin.mp.client;

import com.inbyte.commons.model.dict.WhetherDict;
import jakarta.validation.constraints.NotNull;

/**
 * 微信小程序 URL Link
 * <p>
 * 微信小程序文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/short-link/generateShortLink.html
 *
 * @author chenjw
 */
public interface WxLinkClient {

    /**
     * 生成 URL Link
     */
    String generateUrlLink(String appId,
                           @NotNull String path,
                           String query);

    /**
     * 生成 URL Link
     */
    String generateShortLink(String appId,
                             @NotNull String pageUrl,
                             String pageTitle,
                             WhetherDict permanent);

}
