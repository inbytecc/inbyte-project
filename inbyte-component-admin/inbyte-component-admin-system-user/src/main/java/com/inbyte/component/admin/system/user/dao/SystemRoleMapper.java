package com.inbyte.component.admin.system.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.user.model.system.role.SystemRolePo;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleQuery;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleBrief;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleDetail;
import java.util.List;

/**
 * 角色
 *
 * 表名：  system_role
 * @author chenjw
 * @date 2024-01-18 14:01:44
 */
public interface SystemRoleMapper extends BaseMapper<SystemRolePo> {

    /**
     * 详情
     *
     * @param roleId
     * @return SystemRoleDetail
     **/
    SystemRoleDetail detail(Integer roleId);

    /**
     * 查询列表
     * @param query
     * @return List<SystemRoleBrief>
     **/
    List<SystemRoleBrief> list(SystemRoleQuery query);
}
