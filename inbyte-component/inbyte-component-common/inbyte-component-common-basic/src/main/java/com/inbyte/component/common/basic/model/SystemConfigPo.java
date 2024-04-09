package com.inbyte.component.common.basic.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 系统配置实体
 *
 * @author chenjw
 * @date 2024-03-11 11:03:58
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("system_config")
public class SystemConfigPo {

    /**
      * 配置id
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
      * 配置项key
      */
    @TableField("`key`")
    private String key;

    /**
      * 配置值
      */
    private String value;

    /**
      * 配置项描述字段
      */
    private String remark;

    /**
      * 逻辑删除
      */
    private Integer deleted;

    /**
      * 创建人id
      */
    private Integer creatorId;

    /**
      * 创建人姓名
      */
    private String creatorName;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

    /**
      * 最近更新人id
      */
    private Integer modifierId;

    /**
      * 最近更新人姓名
      */
    private String modifierName;

    /**
      * 最近更新时间
      */
    private LocalDateTime updateTime;

}
