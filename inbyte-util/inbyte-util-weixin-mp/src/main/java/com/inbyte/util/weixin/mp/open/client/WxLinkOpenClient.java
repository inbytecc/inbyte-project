package com.inbyte.util.weixin.mp.open.client;

import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.util.weixin.mp.client.WxLinkClient;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信小程序 URL Link
 *
 * 微信小程序文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/short-link/generateShortLink.html
 * @author chenjw
 */
@Slf4j
@Component
public class WxLinkOpenClient implements WxLinkClient {


    /**
     * 生成 URL Link
     */
    public String generateUrlLink(String appId,
                                     @NotNull String path,
                                     String query) {
        return null;
    }

    /**
     * 生成 URL Link
     */
    public String generateShortLink(String appId,
                                       @NotNull String pageUrl,
                                       String pageTitle,
                                       WhetherDict permanent) {
        return null;
    }

}
