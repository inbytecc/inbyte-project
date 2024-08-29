package com.inbyte.component.admin.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.model.dto.Dict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.dao.InbyteSystemRoleMapper;
import com.inbyte.component.admin.system.user.model.system.role.*;
import com.inbyte.component.admin.system.user.service.SystemRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色服务
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private InbyteSystemRoleMapper inbyteSystemRoleMapper;

    @Override
    public R insert(SystemRoleInsert insert) {
        InbyteSystemRolePo inbyteSystemRolePo = InbyteSystemRolePo.builder()
                .createTime(LocalDateTime.now())
                .creator(SessionUtil.getUserName())
                .mctNo(SessionUtil.getMctNo())
                .build();
        BeanUtils.copyProperties(insert, inbyteSystemRolePo);
        inbyteSystemRoleMapper.insert(inbyteSystemRolePo);
        return R.ok("新增成功");
    }

    @Override
    public R delete(String roleId) {
        LambdaQueryWrapper<InbyteSystemRolePo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(InbyteSystemRolePo::getRoleId, roleId);
        queryWrapper.eq(InbyteSystemRolePo::getMctNo, SessionUtil.getMctNo());
        inbyteSystemRoleMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(SystemRoleUpdate update) {
        InbyteSystemRolePo inbyteSystemRolePo = InbyteSystemRolePo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, inbyteSystemRolePo);

        LambdaQueryWrapper<InbyteSystemRolePo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(InbyteSystemRolePo::getRoleId, update.getRoleId());
        queryWrapper.eq(InbyteSystemRolePo::getMctNo, SessionUtil.getMctNo());
        inbyteSystemRoleMapper.update(inbyteSystemRolePo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<SystemRoleDetail> detail(String roleId) {
        return R.ok(inbyteSystemRoleMapper.detail(roleId, SessionUtil.getMctNo()));
    }

    @Override
    public R<Page<SystemRoleBrief>> list(SystemRoleQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getMctNo());
        return R.page(inbyteSystemRoleMapper.list(query));
    }

    @Override
    public R<List<Dict>> dict() {
        return R.ok(inbyteSystemRoleMapper.dict());
    }
}
