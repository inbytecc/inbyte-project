package com.inbyte.component.admin.marketing;

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
@MapperScan("com.inbyte.component.admin.marketing.dao")
public class MarketingConfiguration {

}
