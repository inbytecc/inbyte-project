package com.inbyte.component.admin.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.SystemUserJwtUtil;
import com.inbyte.component.admin.system.user.dao.InbyteSystemRoleMapper;
import com.inbyte.component.admin.system.user.dao.InbyteSystemUserMapper;
import com.inbyte.component.admin.system.user.model.system.role.InbyteSystemRolePo;
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
    private InbyteSystemUserMapper inbyteSystemUserMapper;
    @Autowired
    private InbyteSystemRoleMapper inbyteSystemRoleMapper;

    @Override
    public R<SystemUserLoginDto> idPwdLogin(SystemUserLoginParam param) {
        param.setPwd(MD5Util.md5(param.getPwd()));
        SystemUserDetail detail = inbyteSystemUserMapper.queryByPwd(param);
        if (detail == null) {
            return R.failure("请联系管理员注册账号");
        }

        InbyteSystemUserPo platformPo = InbyteSystemUserPo.builder()
                .userId(detail.getUserId())
                .latestLoginTime(LocalDateTime.now())
                .loginWay("id-pwd")
                .build();
        inbyteSystemUserMapper.updateById(platformPo);

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
        InbyteSystemUserPo detail = inbyteSystemUserMapper.selectById(SessionUtil.getUserId());
        SystemUserInfo systemUserInfo = new SystemUserInfo();
        systemUserInfo.setUserName(detail.getUserName());
        systemUserInfo.setAvatar(detail.getAvatar());
        systemUserInfo.setRole(detail.getRole());
        systemUserInfo.setNeedUpdatePwd(detail.getNeedUpdatePwd());
        return R.ok(systemUserInfo);
    }

    @Override
    public R<List<Dict>> dict(String keyword) {
        return R.ok(inbyteSystemUserMapper.dict(keyword));
    }

    @Override
    public R insert(SystemUserInsert insert) {
        InbyteSystemUserPo inbyteSystemUserPo = InbyteSystemUserPo.builder()
                .mctNo(SessionUtil.getDefaultMctNo())
                .createTime(LocalDateTime.now())
                .creator(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(insert, inbyteSystemUserPo);
        inbyteSystemUserPo.setPwd(MD5Util.md5(insert.getPwd()));
        inbyteSystemUserPo.setNeedUpdatePwd(1);
        inbyteSystemUserMapper.insert(inbyteSystemUserPo);
        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer userId) {
        LambdaQueryWrapper<InbyteSystemUserPo> queryWrapper = new LambdaQueryWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getUserId, userId)
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getDefaultMctNo());
        inbyteSystemUserMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(SystemUserUpdate update) {
        InbyteSystemUserPo inbyteSystemUserPo = InbyteSystemUserPo.builder()
                .modifier(SessionUtil.getUserName())
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, inbyteSystemUserPo);

        if (update.getRole() != null && update.getRole().size() > 0) {
            List<InbyteSystemRolePo> inbyteSystemRolePos = inbyteSystemRoleMapper.selectBatchIds(update.getRole());
            String roleDesc = inbyteSystemRolePos.stream().map(InbyteSystemRolePo::getName).collect(Collectors.joining(","));
            inbyteSystemUserPo.setRoleDesc(roleDesc);
        }

        LambdaUpdateWrapper<InbyteSystemUserPo> queryWrapper = new LambdaUpdateWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getUserId, update.getUserId())
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getDefaultMctNo());
        inbyteSystemUserMapper.update(inbyteSystemUserPo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<SystemUserDetail> detail(Integer userId) {
        return R.ok(inbyteSystemUserMapper.detail(userId));
    }

    @Override
    public R<Page<SystemUserBrief>> list(SystemUserQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        return R.page(inbyteSystemUserMapper.list(query));
    }

    @Override
    public R updatePwd(SystemUserPwdUpdate update) {
        LambdaUpdateWrapper<InbyteSystemUserPo> updateWrapper = new LambdaUpdateWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getUserId, SessionUtil.getUserId())
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getDefaultMctNo())
                .set(InbyteSystemUserPo::getPwd, MD5Util.md5(update.getPwd()))
                .set(InbyteSystemUserPo::getNeedUpdatePwd, WhetherDict.No.code);
        inbyteSystemUserMapper.update(updateWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R resetPwd(Integer userId) {
        InbyteSystemUserPo inbyteSystemUserPo = inbyteSystemUserMapper.selectById(SessionUtil.getUserId());
        if (inbyteSystemUserPo.getAdmin() == WhetherDict.No.code) {
            return R.failure("只有超管可以重置用户密码");
        }

        LambdaUpdateWrapper<InbyteSystemUserPo> updateWrapper = new LambdaUpdateWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getUserId, userId)
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getDefaultMctNo())
                .set(InbyteSystemUserPo::getPwd, Initial_Password)
                .set(InbyteSystemUserPo::getNeedUpdatePwd, WhetherDict.Yes.code)
                .set(InbyteSystemUserPo::getModifier, SessionUtil.getUserName())
                .set(InbyteSystemUserPo::getUpdateTime, LocalDateTime.now());
        inbyteSystemUserMapper.update(updateWrapper);
        return R.ok("修改成功");
    }
}
