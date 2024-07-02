package com.inbyte.component.app.aliyun.oss.model.storage;

import com.inbyte.commons.model.enums.UploadByEnum;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 对象存储摘要
 *
 * @author chenjw
 * @date 2023-05-10 14:53:36
 **/
@Getter
@Setter
public class ObjectStorageBrief {

    /** 对象ID */
    private Integer objectId;

    /** 文件地址 */
    private String url;

    /** 访问域名 */
    private String endPoint;

    /** oss存储桶 */
    private String bucket;

    /** 商户号 */
    private String mctNo;

    /** 场馆ID */
    private String venueId;

    /** 文件名 */
    private String fileName;

    /** 文件路径 */
    private String filePath;

    /** 模块路径 */
    private String path;

    /** 模块路径参数 */
    private Object pathParam;

    /** 已上传 */
    private Integer uploaded;

    /** 文件大小(单位：字节) */
    private Integer size;

    /** 文件类型 */
    private String fileType;

    /** 文件类型回调 */
    private String mimeType;

    /** 上传源 */
    private UploadByEnum uploadBy;

    /** 高度 */
    private Integer height;

    /** 宽度 */
    private Integer width;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

}
