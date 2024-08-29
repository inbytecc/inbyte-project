package com.inbyte.component.admin.system.user.service;

import com.inbyte.commons.model.dto.Dict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.system.role.*;

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
    R delete(String roleId);

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
    R<SystemRoleDetail> detail(String roleId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<SystemRoleBrief>>>
     **/
    R<Page<SystemRoleBrief>> list(SystemRoleQuery query);

    R<List<Dict>> dict();
}
