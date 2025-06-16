package com.inbyte.component.iot.bisenpark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 百胜停车场接口
 *
 * @author chenjw
 * @date 20250616
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.iot.bisenpark.dao")
public class ComponentIotBisenParkConfiguration {
}
