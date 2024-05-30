package com.inbyte.component.admin.aliyun.oss.model.object.storage;

import com.inbyte.commons.model.dict.FileTypeEnum;
import com.inbyte.commons.model.dict.UploadByEnum;
import com.inbyte.commons.util.serialize.ReadableByte;
import com.inbyte.component.common.dict.convert.DictSerialize;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 对象存储摘要
 *
 * @author chenjw
 * @date 2023-03-14 14:35:14
 **/
@Getter
@Setter
public class ObjectStorageBrief {

    /** 对象ID */
    private Integer objectId;

    /** 地址 */
    private String url;

    /** 访问域名 */
    private String endPoint;

    /** oss存储桶 */
    private String bucket;

    /** 文件名 */
    private String name;

    /** 文件路径 */
    private String path;

    /** 已上传 */
    @DictSerialize
    private Integer uploaded;

    /** 文件大小(单位：字节) */
    @ReadableByte
    private Integer size;

    /** 文件类型 */
    @DictSerialize(FileTypeEnum.class)
    private FileTypeEnum fileType;

    /** 文件类型回调 */
    private String mimeType;

    /** 上传源 */
    @DictSerialize(UploadByEnum.class)
    private UploadByEnum uploadBy;

    /** 高度 */
    private Integer height;

    /** 宽度 */
    private Integer width;

    /** 创建人 */
    private String creator;

    /** 创建时间 */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

}