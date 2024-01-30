package com.inbyte.component.admin.system.user.model.system.role;

import com.alibaba.fastjson2.JSONArray;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 角色摘要
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
@Getter
@Setter
public class SystemRoleBrief {

    /** 角色ID */
    private Integer roleId;

    /** 名称 */
    private String name;

    /** 菜单 */
    private JSONArray menu;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

}
