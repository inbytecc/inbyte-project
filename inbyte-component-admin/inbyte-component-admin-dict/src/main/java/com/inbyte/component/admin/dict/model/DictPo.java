package com.inbyte.component.admin.dict.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 字典实体
 *
 * @author chenjw
 * @date 2023-12-17 22:12:01
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("dict")
public class DictPo {

    /**
      * ID
      */
    @TableId(value = "dictId", type = IdType.AUTO)
    private Integer dictId;

    /**
     * 名字
     */
    private String name;

    /**
     * 标签
     */
    private String label;

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
      * 创建人ID
      */
    private Integer createUserId;

    /**
      * 创建人名字
      */
    private String createUserName;

    /**
      * 更新时间
      */
    private LocalDateTime updateTime;

    /**
     * 商户号
     */
    private String mctNo;

}
