package com.inbyte.component.app;

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
@MapperScan(basePackages = {"com.inbyte.component.app.payment.weixin",
        "com.inbyte.component.app.order.dao"
})
public class OrderPaymentConfiguration {
}
