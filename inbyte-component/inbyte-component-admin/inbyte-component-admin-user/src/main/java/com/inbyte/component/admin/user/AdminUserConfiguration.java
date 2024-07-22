package com.inbyte.component.admin.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 营销
 *
 * @author chenjw
 * @date 2018年1月18日
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.admin.user.dao")
public class AdminUserConfiguration {

}
