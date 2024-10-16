package com.inbyte.component.app.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 订单支付
 *
 * @author chenjw
 */
@ComponentScan
@Configuration
@MapperScan(basePackages = "com.inbyte.component.app.order.dao")
public class OrderConfiguration {
}
