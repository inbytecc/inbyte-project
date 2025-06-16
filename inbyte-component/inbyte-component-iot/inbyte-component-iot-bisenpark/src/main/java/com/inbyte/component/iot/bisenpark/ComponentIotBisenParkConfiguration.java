package com.inbyte.component.iot.bisenpark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 百胜停车场接口配置
 *
 * @author chenjw
 * @date 2025-01-16
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.iot.bisenpark.dao")
@EnableConfigurationProperties
public class ComponentIotBisenParkConfiguration {
}
