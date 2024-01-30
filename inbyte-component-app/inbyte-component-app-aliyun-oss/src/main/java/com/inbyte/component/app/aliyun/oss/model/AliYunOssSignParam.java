package com.inbyte.component.app.aliyun.oss.model;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OSS安全令牌对象
 *
 * @author chenjw
 * @date 2020/08/04 04:35:21
 */
@Getter
@Setter
@ToString
public class AliYunOssSignParam {

    /**
     * 页面
     * 小程序页面地址
     * 等于 page
     **/
    @NotNull(message = "上传资源所在页面路径不能为空")
    private String path;

    /**
     * 文件名称
     **/
    @NotNull(message = "文件名称")
    private String fileName;

    /**
     * 文件类型
     */
    private Integer fileType;
}