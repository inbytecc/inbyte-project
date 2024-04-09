package com.inbyte.component.app.sign;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 用户组件
 *
 * @author chenjw
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.app.user.dao")
public class AppSignConfiguration {
}
