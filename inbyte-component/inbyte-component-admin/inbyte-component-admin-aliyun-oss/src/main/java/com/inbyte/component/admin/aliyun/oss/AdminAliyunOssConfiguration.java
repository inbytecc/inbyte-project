package com.inbyte.component.admin.aliyun.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS组件
 *
 * @author chenjw
 */
@ComponentScan
@Configuration
@MapperScan(basePackages = "com.inbyte.component.admin.aliyun.oss.dao")
public class AdminAliyunOssConfiguration {
}
