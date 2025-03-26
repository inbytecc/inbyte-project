package com.inbyte.component.common.ai.customer.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AI客服
 *
 * @author chenjw
 * @date 20240315
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.common.ai.customer.service.dao")
public class ComponentAiCustomerServiceConfiguration {
}
