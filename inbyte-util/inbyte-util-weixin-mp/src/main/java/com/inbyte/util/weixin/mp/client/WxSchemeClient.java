package com.inbyte.util.weixin.mp.client;

import jakarta.validation.constraints.NotNull;

/**
 * 微信小程序 URL Scheme
 *
 * @author chenjw
 */
public interface WxSchemeClient {

    /**
     * 生成Scheme Code
     */
    String generateScheme(String appId,
                          @NotNull String path,
                          String query);

}
