package com.inbyte.component.admin.system.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.user.model.system.role.InbyteSystemRolePo;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleQuery;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleBrief;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色
 *
 * 表名：  inbyte_system_role
 * @author chenjw
 * @date 2024-01-18 14:01:44
 */
public interface InbyteSystemRoleMapper extends BaseMapper<InbyteSystemRolePo> {

    /**
     * 详情
     *
     * @param roleId
     * @return SystemRoleDetail
     **/
    SystemRoleDetail detail(@Param("roleId") Integer roleId,
                            @Param("mctNo") String mctNo);

    /**
     * 查询列表
     * @param query
     * @return List<SystemRoleBrief>
     **/
    List<SystemRoleBrief> list(SystemRoleQuery query);
}
