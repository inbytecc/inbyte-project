package com.inbyte.util.weixin.mp.open;

import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
//@AllArgsConstructor
@ConditionalOnProperty(prefix = "wx.open", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(WxOpenProperties.class)
public class WxOpenConfiguration {
    private final WxOpenProperties properties;

//    @Autowired
//    private StringRedisTemplate redisTemplate;

    @Autowired
    public WxOpenConfiguration(WxOpenProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WxOpenService wxOpenService() {
        WxOpenService wxOpenService = new WxOpenServiceImpl();
//        Set<String> wx = redisTemplate.keys("wx");
//        System.out.println(wx);
//        WxOpenInRedisTemplateConfigStorage inRedisConfigStorage = new WxOpenInRedisTemplateConfigStorage(redisTemplate, "wx:open");
//        inRedisConfigStorage.setComponentAppId(properties.getComponentAppId());
//        inRedisConfigStorage.setComponentAppSecret(properties.getComponentSecret());
//        inRedisConfigStorage.setComponentToken(properties.getComponentToken());
//        inRedisConfigStorage.setComponentAesKey(properties.getComponentAesKey());
//        wxOpenService.setWxOpenConfigStorage(inRedisConfigStorage);
        return wxOpenService;
    }

    @Bean
    public WxOpenMessageRouter wxOpenMessageRouter(WxOpenService wxOpenService) {
        return new WxOpenMessageRouter(wxOpenService);
    }
} 