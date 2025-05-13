package com.inbyte.util.weixin.mp.ma.client;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.scheme.WxMaGenerateSchemeRequest;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.exception.BizException;
import com.inbyte.util.weixin.mp.client.WxSchemeClient;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信小程序 URL Scheme
 *
 * @author chenjw
 */
@Slf4j
@Component
public class WxSchemeMaClient implements WxSchemeClient {

    @Autowired
    private WxMaService wxMaService;

    /**
     * 生成Scheme Code
     */
    public String generateScheme(String appId,
                                    @NotNull String path,
                                    String query) {
        if (!wxMaService.switchover(appId)) {
            log.error("Scheme码生成, 未找到对应appId={}, 的配置, 请核实！", appId);
            throw BizException.fail("服务错误, 稍等一下马上就好");
        }

        try {
            WxMaGenerateSchemeRequest.JumpWxa jumpWxa = WxMaGenerateSchemeRequest.JumpWxa.newBuilder()
                    .path(path)
                    .query(query)
                    .envVersion("release")
                    .build();
            WxMaGenerateSchemeRequest schemeRequest = WxMaGenerateSchemeRequest.newBuilder()
                    .jumpWxa(jumpWxa)
                    .expireType(1)
                    .expireInterval(30)
                    .build();
            log.info("Scheme码生成, 参数:{}", JSON.toJSONString(schemeRequest));
            String generate = wxMaService.getWxMaSchemeService().generate(schemeRequest);
            return generate;
        } catch (WxErrorException e) {
            log.error("Scheme码生成", e);
            throw BizException.fail("Scheme码生成, 稍等一下马上就好");
        } finally {
            //清理ThreadLocal
            WxMaConfigHolder.remove();
        }
    }


}
