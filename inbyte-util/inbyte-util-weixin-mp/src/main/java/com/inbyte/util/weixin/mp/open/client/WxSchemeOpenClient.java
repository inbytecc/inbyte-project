package com.inbyte.util.weixin.mp.open.client;

import com.inbyte.util.weixin.mp.client.WxSchemeClient;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信小程序 URL Scheme
 *
 * @author chenjw
 */
@Slf4j
@Component
public class WxSchemeOpenClient implements WxSchemeClient {


    /**
     * 生成Scheme Code
     */
    public String generateScheme(String appId,
                                    @NotNull String path,
                                    String query) {

        return null;
    }


}
