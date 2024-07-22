package com.inbyte.component.admin.system.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 商户用户 Session 管理
 *
 * @author chenjw
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.admin.system.user.dao")
public class SystemUserConfiguration {
}
