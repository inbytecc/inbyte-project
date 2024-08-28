package com.inbyte.component.common.payment;

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
@MapperScan(basePackages = {"com.inbyte.component.common.payment.weixin.dao",
        "com.inbyte.component.common.payment.common.weixin.dao"})
public class PaymentConfiguration {
}
