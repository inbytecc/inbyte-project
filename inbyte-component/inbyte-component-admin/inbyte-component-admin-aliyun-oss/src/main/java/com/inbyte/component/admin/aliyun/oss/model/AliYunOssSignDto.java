package com.inbyte.component.admin.aliyun.oss.model;


import lombok.*;

/**
 * OSS安全令牌对象
 *
 * @author chenjw
 * @date 2020/08/04 04:35:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AliYunOssSignDto {

    /**
     * 访问ID
     */
    private String accessId;
    /**
     * 存放策略
     */
    private String policy;
    /**
     * 签名
     */
    private String signature;
    /**
     * 文件存放目录
     */
    private String dir;
    /**
     * 服务地址
     */
    private String host;
    /**
     * 过期时间
     */
    private long expire;
    /**
     * 回调参数
     */
    private String callback;
}