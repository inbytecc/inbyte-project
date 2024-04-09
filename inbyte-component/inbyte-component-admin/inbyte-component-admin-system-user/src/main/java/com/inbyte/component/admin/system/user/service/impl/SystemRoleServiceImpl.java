package com.inbyte.component.admin.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.dao.SystemRoleMapper;
import com.inbyte.component.admin.system.user.service.SystemRoleService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.system.user.model.system.role.SystemRolePo;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleQuery;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleInsert;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleUpdate;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleBrief;
import com.inbyte.component.admin.system.user.model.system.role.SystemRoleDetail;
import com.inbyte.commons.util.PageUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * 角色服务
 *
 * @author chenjw
 * @date 2024-01-18 14:01:44
 **/
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Override
    public R insert(SystemRoleInsert insert) {
        SystemRolePo systemRolePo = SystemRolePo.builder()
                .createTime(LocalDateTime.now())
                .creatorId(SessionUtil.getUserId())
                .creatorName(SessionUtil.getUserName())
                .mctNo(SessionUtil.getDefaultMctNo())
                .build();
        BeanUtils.copyProperties(insert, systemRolePo);
        systemRoleMapper.insert(systemRolePo);
        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer roleId) {
        LambdaQueryWrapper<SystemRolePo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SystemRolePo::getRoleId, roleId);
        queryWrapper.eq(SystemRolePo::getMctNo, SessionUtil.getDefaultMctNo());
        systemRoleMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(SystemRoleUpdate update) {
        SystemRolePo systemRolePo = SystemRolePo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, systemRolePo);

        LambdaQueryWrapper<SystemRolePo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SystemRolePo::getRoleId, update.getRoleId());
        queryWrapper.eq(SystemRolePo::getMctNo, SessionUtil.getDefaultMctNo());
        systemRoleMapper.update(systemRolePo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<SystemRoleDetail> detail(Integer roleId) {
        return R.ok(systemRoleMapper.detail(roleId));
    }

    @Override
    public R<Page<SystemRoleBrief>> list(SystemRoleQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getDefaultMctNo());
        return R.page(systemRoleMapper.list(query));
    }
}
