package com.inbyte.component.admin.system.user.controller;

import com.inbyte.commons.model.dto.Dict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    /**
//     * 新增
//     *
//     * @param insert
//     * @return Result
//     **/
//    @PostMapping
//    public R insert(@RequestBody @Valid SystemRoleInsert insert) {
//        return systemRoleService.insert(insert);
//    }
//
//    /**
//     * 删除
//     *
//     * @param roleId
//     * @return Result
//     **/
//    @DeleteMapping("{roleId}")
//    public R delete(@PathVariable("roleId") String roleId) {
//        return systemRoleService.delete(roleId);
//    }
//
//    /**
//     * 更新
//     *
//     * @param update
//     * @return Result
//     **/
//    @PutMapping
//    public R update(@RequestBody @Valid SystemRoleUpdate update) {
//        return systemRoleService.update(update);
//    }
//
//    /**
//     * 详情
//     *
//     * @param roleId
//     * @return Result<SystemRoleDetail>
//     **/
//    @GetMapping("{roleId}")
//    public R<SystemRoleDetail> detail(@PathVariable("roleId") String roleId) {
//        return systemRoleService.detail(roleId);
//    }
//
//    /**
//     * 列表
//     *
//     * @param query
//     * @return Result<Page<List<SystemRoleBrief>>>
//     **/
//    @GetMapping
//    public R<Page<SystemRoleBrief>> list(@ModelAttribute @Valid SystemRoleQuery query) {
//        return systemRoleService.list(query);
//    }

    /**
     * 字典
     *
     * @return R<Dict>
     **/
    @GetMapping("dict")
    public R<List<Dict>> dict() {
        return systemRoleService.dict();
    }

}
