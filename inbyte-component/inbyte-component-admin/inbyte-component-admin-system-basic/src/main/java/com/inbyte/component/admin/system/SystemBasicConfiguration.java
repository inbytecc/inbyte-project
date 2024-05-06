package com.inbyte.component.admin.system;

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
@MapperScan({"com.inbyte.component.admin.system.basic.dao", "com.inbyte.component.admin.system.dict.dao"})
public class SystemBasicConfiguration {
}
