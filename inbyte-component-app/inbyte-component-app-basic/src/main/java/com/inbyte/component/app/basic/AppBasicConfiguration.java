package com.inbyte.component.app.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置管理
 *
 * @author chenjw
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.app.basic.dao")
public class AppBasicConfiguration {
}
