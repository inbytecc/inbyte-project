
package com.inbyte.component.admin.aliyun.oss.model.object.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


/**
 * 对象存储创建
 *
 * @author chenjw
 * @date 2023-03-15 16:53:59
 **/
@Getter
@Setter
public class ObjectStorageUpdate {

    /**
     * 对象ID
     */
    @NotNull
    private Integer objectId;

    /** 宽度 */
    private String remark;

    @JsonIgnore
    private String mctNo;
}
