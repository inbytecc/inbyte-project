package com.inbyte.component.admin.system.user.controller;
import com.inbyte.component.admin.system.user.service.SystemRoleService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleQuery;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleInsert;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleUpdate;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleBrief;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 角色
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
@RestController
@RequestMapping("system/role")
public class SystemRoleController {

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    @PostMapping
    public R insert(@RequestBody @Valid SystemRoleInsert insert) {
        return systemRoleService.insert(insert);
    }

    /**
     * 删除
     *
     * @param roleId
     * @return Result
     **/
    @DeleteMapping("{roleId}")
    public R delete(@PathVariable("roleId") Integer roleId) {
        return systemRoleService.delete(roleId);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid SystemRoleUpdate update) {
        return systemRoleService.update(update);
    }

    /**
     * 详情
     *
     * @param roleId
     * @return Result<SystemRoleDetail>
     **/
    @GetMapping("{roleId}")
    public R<SystemRoleDetail> detail(@PathVariable("roleId") Integer roleId) {
        return systemRoleService.detail(roleId);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<SystemRoleBrief>>>
     **/
    @GetMapping
    public R<Page<SystemRoleBrief>> list(@ModelAttribute @Valid SystemRoleQuery query) {
        return systemRoleService.list(query);
    }
}
