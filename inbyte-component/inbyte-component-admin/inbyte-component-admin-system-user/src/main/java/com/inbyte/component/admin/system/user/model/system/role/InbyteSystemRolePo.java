package com.inbyte.component.admin.system.user.model.system.role;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 角色实体
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_system_role")
public class InbyteSystemRolePo {

    /**
      * 角色ID
      */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
      * 名称
      */
    private String name;

    /**
      * 菜单
      */
    private JSONArray menu;

    /**
      * 逻辑删除
      */
    private Integer deleted;

    /**
      * 备注
      */
    private String remark;

    /**
      * 创建人
      */
    private Integer creatorId;

    /**
      * 修改人
      */
    private String creator;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

    /**
      * 修改人
      */
    private Integer modifierId;

    /**
      * 修改时间
      */
    private LocalDateTime updateTime;

    /**
     * 默认商户号
     */
    private String mctNo;
}
