package com.inbyte.component.admin.system.user.service;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleQuery;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleInsert;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleUpdate;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleBrief;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleDetail;

import java.util.List;

/**
 * 角色服务
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
public interface SystemRoleService {

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    R insert(SystemRoleInsert insert);

    /**
     * 删除
     *
     * @param roleId
     * @return Result
     **/
    R delete(Integer roleId);

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(SystemRoleUpdate update);

    /**
     * 详情
     *
     * @param roleId
     * @return SystemRoleDetail
     **/
    R<SystemRoleDetail> detail(Integer roleId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<SystemRoleBrief>>>
     **/
    R<Page<SystemRoleBrief>> list(SystemRoleQuery query);
}
