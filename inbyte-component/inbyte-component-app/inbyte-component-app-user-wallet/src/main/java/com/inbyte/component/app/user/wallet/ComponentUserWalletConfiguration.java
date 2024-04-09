package com.inbyte.component.app.user.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 用户钱包组件
 *
 * @author chenjw
 */
@ComponentScan
@Configuration
@MapperScan("com.inbyte.component.app.user.wallet.dao")
public class ComponentUserWalletConfiguration {
}
