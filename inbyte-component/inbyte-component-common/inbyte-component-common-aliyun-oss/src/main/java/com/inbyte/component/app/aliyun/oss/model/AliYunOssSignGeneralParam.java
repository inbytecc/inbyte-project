package com.inbyte.component.app.aliyun.oss.model;


import com.inbyte.commons.model.enums.FileTypeEnum;
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
public class AliYunOssSignGeneralParam {

    /**
     * 文件名称
     **/
    @NotNull
    private String fileName;

    /**
     * 文件类型
     */
    @NotNull
    private FileTypeEnum fileType;

    /**
     * 是否可定期清除
     */
    @NotNull
    private Integer deletable;

    @NotNull
    private String mctNo;
}