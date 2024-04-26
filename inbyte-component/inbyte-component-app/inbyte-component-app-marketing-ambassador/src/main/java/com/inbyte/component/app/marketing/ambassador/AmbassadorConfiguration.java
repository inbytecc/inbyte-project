package com.inbyte.component.app.marketing.ambassador;

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
@MapperScan(basePackages = "com.inbyte.component.app.user.weixin.mp.dao")
public class AmbassadorConfiguration {
}
