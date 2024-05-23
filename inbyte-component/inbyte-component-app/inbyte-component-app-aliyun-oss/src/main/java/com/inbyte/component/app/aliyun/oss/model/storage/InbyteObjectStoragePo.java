package com.inbyte.component.app.aliyun.oss.model.storage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.dict.FileTypeEnum;
import com.inbyte.commons.model.dict.UploadByEnum;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 对象存储实体
 *
 * @author chenjw
 * @date 2024-03-12 09:40:07
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_object_storage")
public class InbyteObjectStoragePo {

    /**
     * 对象ID
     */
    @TableId(value = "object_id", type = IdType.AUTO)
    private Integer objectId;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 访问域名
     */
    private String endPoint;

    /**
     * oss存储桶
     */
    private String bucket;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 模块路径
     */
    private String path;

    /**
     * 模块路径参数
     */
    private String pathParam;

    /**
     * 已上传
     */
    private Integer uploaded;

    /**
     * 文件大小(单位：字节)
     */
    private Integer size;

    /**
     * 文件类型
     */
    private FileTypeEnum fileType;

    /**
     * 文件类型回调
     */
    private String mimeType;

    /**
     * 上传源
     */
    private UploadByEnum uploadSource;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 备注
     */
    private String remark;

    /**
     * 已删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String modifier;

}

