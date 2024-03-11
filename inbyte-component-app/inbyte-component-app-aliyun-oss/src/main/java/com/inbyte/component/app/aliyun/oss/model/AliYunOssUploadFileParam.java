package com.inbyte.component.app.aliyun.oss.model;

import lombok.*;

/**
 * OSS安全令牌对象
 *
 * @author chenjw
 * @date 2020/08/04 04:35:21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AliYunOssUploadFileParam {


    /**
     * 页面
     * 小程序页面地址
     * 等于 page
     **/
    private String page;

    /**
     * 进入页面地址所需参数
     * JSON对象类型
     * 可空
     **/
    private String scene;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件二进制数据
     */
    private byte[] fileBytes;

    /**
     * 可覆盖
     */
    private Integer coverable;

    /**
     * 商户名
     */
    private String merchantName;
    /**
     * 上传用户ID
     */
    private String userId;
    /**
     * 上传用户名
     */
    private String userName;

}