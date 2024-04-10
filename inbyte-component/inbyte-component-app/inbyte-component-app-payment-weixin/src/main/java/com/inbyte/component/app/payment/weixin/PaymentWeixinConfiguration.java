package com.inbyte.component.app.payment.weixin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付
 *
 * @author chenjw
 */
@ComponentScan
@Configuration
@MapperScan(basePackages = "com.inbyte.component.app.payment.weixin")
public class PaymentWeixinConfiguration {
}
