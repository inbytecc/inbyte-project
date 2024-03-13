package com.inbyte.component.admin.aliyun.oss.model.object.storage;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


/**
 * 对象存储详情
 *
 * @author chenjw
 * @date 2023-03-14 14:35:14
 **/
@Getter
@Setter
public class ObjectStorageDetail {

    /** 对象ID */
    private Integer objectId;

    /** 访问域名 */
    private String endPoint;

    /** oss存储桶 */
    private String bucket;

    /** 商户ID */
    private String mctNo;

    /** 文件名 */
    private String fileName;

    /** 文件路径 */
    private String filePath;

    /** 已上传 */
    private Integer uploaded;

    /** 文件大小(单位：字节) */
    private Integer size;

    /** 文件类型 */
    private String fileType;

    /** 文件类型回调 */
    private String mimeType;

    /** 上传源 */
    private Integer uploadSource;

    /** 高度 */
    private Integer height;

    /** 宽度 */
    private Integer width;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 创建人ID */
    private Integer creatorId;

    /** 创建人名字 */
    private String creatorName;

    /** 更新时间 */
    private LocalDateTime updateTime;

}
