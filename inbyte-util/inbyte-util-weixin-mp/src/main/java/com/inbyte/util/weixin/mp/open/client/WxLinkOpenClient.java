package com.inbyte.util.weixin.mp.open.client;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.shortlink.GenerateShortLinkRequest;
import cn.binarywang.wx.miniapp.bean.urllink.GenerateUrlLinkRequest;
import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.exception.BizException;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.util.weixin.mp.client.WxLinkClient;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WxOpenService wxOpenService;


    /**
     * 生成 URL Link
     */
    public String generateUrlLink(String appId,
                                     @NotNull String path,
                                     String query) {
        try {
            WxMaService wxMaService = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId);
            GenerateUrlLinkRequest generateUrlLinkRequest = GenerateUrlLinkRequest.builder()
                    .path(path)
                    .query(query)
                    .envVersion("release")
                    .build();
            log.info("URL Link 生成, jsCode:{}", JSON.toJSONString(generateUrlLinkRequest));
            String generate = wxMaService.getLinkService().generateUrlLink(generateUrlLinkRequest);
            log.info("URL Link 生成, 返回结果:{}", generate);
            return generate;
        } catch (WxErrorException e) {
            log.error("URL Link 生成", e);
            throw BizException.fail("URL Link 生成, 稍等一下马上就好");
        }
    }

    /**
     * 生成 URL Link
     */
    public String generateShortLink(String appId,
                                       @NotNull String pageUrl,
                                       String pageTitle,
                                       WhetherDict permanent) {
        try {
            WxMaService wxMaService = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId);
            GenerateShortLinkRequest generateUrlLinkRequest = GenerateShortLinkRequest.builder()
                    .pageUrl(pageUrl)
                    .pageTitle(pageTitle)
                    .isPermanent(permanent.yes())
                    .build();
            log.info("URL Link 生成, 参数:{}", JSON.toJSONString(generateUrlLinkRequest));
            String generate = wxMaService.getLinkService().generateShortLink(generateUrlLinkRequest);
            return generate;
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 43104) {
                throw BizException.fail("此小程序暂时没有权限生成短链");
            }
            log.error("URL Link 生成", e);
            throw BizException.fail("URL Link 生成, 稍等一下马上就好");
        }
    }

}
