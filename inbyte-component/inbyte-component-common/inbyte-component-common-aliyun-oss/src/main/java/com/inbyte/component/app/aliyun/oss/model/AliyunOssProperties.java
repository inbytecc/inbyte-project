package com.inbyte.component.app.aliyun.oss.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Getter
@Setter
public class AliyunOssProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String region;
    private String endpoint;
    private String bucketName;
}