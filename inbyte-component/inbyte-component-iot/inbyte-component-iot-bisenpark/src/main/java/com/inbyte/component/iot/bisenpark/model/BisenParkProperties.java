package com.inbyte.component.iot.bisenpark.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 百胜停车场配置属性
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "inbyte.iot.bisenpark")
public class BisenParkProperties {

    /**
     * 百胜停车场API基础地址
     */
    private String baseUrl = "https://cloud.bisenaccess.com";

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 连接超时时间（毫秒）
     */
    private int connectTimeout = 30000;

    /**
     * 读取超时时间（毫秒）
     */
    private int readTimeout = 30000;

    /**
     * 是否启用
     */
    private boolean enabled = true;
} 