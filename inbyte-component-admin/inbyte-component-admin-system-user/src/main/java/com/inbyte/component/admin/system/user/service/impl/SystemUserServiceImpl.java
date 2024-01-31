package com.inbyte.component.admin.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.SystemUserJwtUtil;
import com.inbyte.component.admin.system.user.dao.SystemRoleMapper;
import com.inbyte.component.admin.system.user.dao.SystemUserMapper;
import com.inbyte.component.admin.system.user.model.system.role.SystemRolePo;
import com.inbyte.component.admin.system.user.model.system.user.*;
import com.inbyte.component.admin.system.user.service.SystemUserService;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.Dict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.MD5Util;
import com.inbyte.commons.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台用户服务
 *
 * @author chenjw
 * @date 2022/12/20
 */
@Slf4j
@Service
public class SystemUserServiceImpl implements SystemUserService {

    /**
     * 平台用户默认密码
     */
    private static final String Initial_Password = "e10adc3949ba59abbe56e057f20f883e";

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Override
    public R<SystemUserLoginDto> idPwdLogin(SystemUserLoginParam param) {
        param.setPwd(MD5Util.md5(param.getPwd()));
        SystemUserDetail detail = systemUserMapper.queryByPwd(param);
        if (detail == null) {
            return R.failure("请联系管理员注册账号");
        }

        SystemUserPo platformPo = SystemUserPo.builder()
                .userId(detail.getUserId())
                .latestLoginTime(LocalDateTime.now())
                .loginWay("id-pwd")
                .build();
        systemUserMapper.updateById(platformPo);

        SessionUser sessionUser = new SessionUser();
        sessionUser.setUserId(detail.getUserId());
        sessionUser.setUserName(detail.getUserName());
        sessionUser.setTel(detail.getTel());
        sessionUser.setMctNo(detail.getMctNo());
        sessionUser.setMctName(detail.getMctName());
        sessionUser.setMctPinYinName(detail.getPinYinName());
        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
        sessionUser.setLoginTime(LocalDateTime.now());
        sessionUser.setAdmin(detail.getAdmin());
        String jwt = SystemUserJwtUtil.createJwt(sessionUser);
        return R.ok(new SystemUserLoginDto(jwt));
    }

    @Override
    public R<SystemUserInfo> info() {
        SystemUserPo detail = systemUserMapper.selectById(SessionUtil.getUserId());
        SystemUserInfo systemUserInfo = new SystemUserInfo();
        systemUserInfo.setUserName(detail.getUserName());
        systemUserInfo.setAvatar(detail.getAvatar());
        systemUserInfo.setRole(detail.getRole());
        systemUserInfo.setNeedUpdatePwd(detail.getNeedUpdatePwd());
        return R.ok(systemUserInfo);
    }

    @Override
    public R<List<Dict>> dict(String keyword) {
        return R.ok(systemUserMapper.dict(keyword));
    }

    @Override
    public R insert(SystemUserInsert insert) {
        SystemUserPo systemUserPo = SystemUserPo.builder()
                .mctNo(SessionUtil.getDefaultMctNo())
                .createTime(LocalDateTime.now())
                .createUserId(SessionUtil.getUserId())
                .createUserName(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(insert, systemUserPo);
        systemUserPo.setPwd(MD5Util.md5(insert.getPwd()));
        systemUserPo.setNeedUpdatePwd(1);
        systemUserMapper.insert(systemUserPo);
        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer userId) {
        LambdaQueryWrapper<SystemUserPo> queryWrapper = new LambdaQueryWrapper<SystemUserPo>()
                .eq(SystemUserPo::getUserId, userId)
                .eq(SystemUserPo::getMctNo, SessionUtil.getDefaultMctNo());
        systemUserMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(SystemUserUpdate update) {
        SystemUserPo systemUserPo = SystemUserPo.builder()
                .updateUserId(SessionUtil.getUserId())
                .updateUserName(SessionUtil.getUserName())
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, systemUserPo);

        if (update.getRole() != null && update.getRole().size() > 0) {
            List<SystemRolePo> systemRolePos = systemRoleMapper.selectBatchIds(update.getRole());
            String roleDesc = systemRolePos.stream().map(SystemRolePo::getName).collect(Collectors.joining(","));
            systemUserPo.setRoleDesc(roleDesc);
        }

        LambdaUpdateWrapper<SystemUserPo> queryWrapper = new LambdaUpdateWrapper<SystemUserPo>()
                .eq(SystemUserPo::getUserId, update.getUserId())
                .eq(SystemUserPo::getMctNo, SessionUtil.getDefaultMctNo());
        systemUserMapper.update(systemUserPo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<SystemUserDetail> detail(Integer userId) {
        return R.ok(systemUserMapper.detail(userId));
    }

    @Override
    public R<Page<List<SystemUserBrief>>> list(SystemUserQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        return R.page(systemUserMapper.list(query));
    }

    @Override
    public R updatePwd(SystemUserPwdUpdate update) {
        LambdaUpdateWrapper<SystemUserPo> updateWrapper = new LambdaUpdateWrapper<SystemUserPo>()
                .eq(SystemUserPo::getUserId, SessionUtil.getUserId())
                .eq(SystemUserPo::getMctNo, SessionUtil.getDefaultMctNo())
                .set(SystemUserPo::getPwd, MD5Util.md5(update.getPwd()))
                .set(SystemUserPo::getNeedUpdatePwd, WhetherDict.No.code);
        systemUserMapper.update(null, updateWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R resetPwd(Integer userId) {
        SystemUserPo systemUserPo = systemUserMapper.selectById(SessionUtil.getUserId());
        if (systemUserPo.getAdmin() == WhetherDict.No.code) {
            return R.failure("只有超管可以重置用户密码");
        }

        LambdaUpdateWrapper<SystemUserPo> updateWrapper = new LambdaUpdateWrapper<SystemUserPo>()
                .eq(SystemUserPo::getUserId, userId)
                .eq(SystemUserPo::getMctNo, SessionUtil.getDefaultMctNo())
                .set(SystemUserPo::getPwd, Initial_Password)
                .set(SystemUserPo::getNeedUpdatePwd, WhetherDict.Yes.code)
                .set(SystemUserPo::getUpdateUserId, SessionUtil.getUserId())
                .set(SystemUserPo::getUpdateUserName, SessionUtil.getUserName())
                .set(SystemUserPo::getUpdateTime, LocalDateTime.now())
                ;
        systemUserMapper.update(null, updateWrapper);
        return R.ok("修改成功");
    }
}
