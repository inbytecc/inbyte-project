package com.inbyte.component.admin.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.Dict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.commons.util.MD5Util;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.SystemUserJwtUtil;
import com.inbyte.component.admin.system.user.dao.InbyteSystemRoleMapper;
import com.inbyte.component.admin.system.user.dao.InbyteSystemUserMapper;
import com.inbyte.component.admin.system.user.dao.InbyteSystemUserMerchantMapper;
import com.inbyte.component.admin.system.user.model.system.role.InbyteSystemRolePo;
import com.inbyte.component.admin.system.user.model.system.user.*;
import com.inbyte.component.admin.system.user.model.system.user.merchant.InbyteSystemUserMerchantBrief;
import com.inbyte.component.admin.system.user.service.SystemUserService;
import com.inbyte.component.common.basic.dao.InbyteMerchantMapper;
import com.inbyte.component.common.basic.model.InbyteMerchantPo;
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
    private InbyteSystemUserMerchantMapper inbyteSystemUserMerchantMapper;
    @Autowired
    private InbyteSystemRoleMapper inbyteSystemRoleMapper;
    @Autowired
    private InbyteMerchantMapper inbyteMerchantMapper;

    @Override
    public R<SystemUserLoginDto> idPwdLogin(SystemUserLoginParam param) {
        param.setPwd(MD5Util.md5(param.getPwd()));
        SystemUserDetail detail = inbyteSystemUserMapper.queryByPwd(param);
        if (detail == null) {
            return R.failure("账号或密码错误");
        }

        SystemUserLoginDto systemUserLoginDto = new SystemUserLoginDto();
        systemUserLoginDto.setMultipleMerchant(Whether.No);
        List<InbyteSystemUserMerchantBrief> userMerchantPoList = inbyteSystemUserMerchantMapper.listByUserId(detail.getUserId());
        if (userMerchantPoList.size() > 1) {
            systemUserLoginDto.setMultipleMerchant(Whether.Yes);
            systemUserLoginDto.setMerchantList(userMerchantPoList);
        }

        InbyteSystemUserPo platformPo = InbyteSystemUserPo.builder()
                .userId(detail.getUserId())
                .latestLoginTime(LocalDateTime.now())
                .loginWay("id-pwd")
                .build();
        inbyteSystemUserMapper.updateById(platformPo);

        InbyteMerchantPo inbyteMerchantPo = inbyteMerchantMapper.selectById(detail.getMctNo());
        if (inbyteMerchantPo == null) {
            return R.failure("商户信息错误，请联系技术客服处理");
        }

        // 如果微信小程序登录场景，则更新当前用户openID
        SessionUser currentSession = SessionUtil.getSessionUserUnchecked();
        if (currentSession != null && StringUtil.isNotEmpty(currentSession.getOpenId())) {
            LambdaUpdateWrapper<InbyteSystemUserPo> updateWrapper = new LambdaUpdateWrapper<InbyteSystemUserPo>()
                    .eq(InbyteSystemUserPo::getUserId, detail.getUserId())
                    .set(InbyteSystemUserPo::getOpenId, currentSession.getOpenId());
            inbyteSystemUserMapper.update(updateWrapper);
        }

        SessionUser sessionUser = new SessionUser();
        sessionUser.setUserId(detail.getUserId());
        sessionUser.setUserName(detail.getUserName());
        sessionUser.setTel(detail.getTel());
        sessionUser.setMctNo(detail.getMctNo());
        sessionUser.setMctName(inbyteMerchantPo.getMctName());
        sessionUser.setTokenVersion(SessionUtil.User_Token_Version);
        sessionUser.setLoginTime(LocalDateTime.now());
        sessionUser.setAdmin(detail.getAdmin());
        sessionUser.setLoginWay("id-pwd");
        String jwt = SystemUserJwtUtil.createJwt(sessionUser);
        systemUserLoginDto.setUserToken(jwt);
        return R.ok(systemUserLoginDto);
    }


    @Override
    public R<SystemUserInfo> info() {
        SessionUser sessionUser = SessionUtil.getSessionUser();
        InbyteSystemUserPo detail = inbyteSystemUserMapper.selectById(sessionUser.getUserId());
        if (detail == null) {
            return R.set(ResultStatus.Unauthorized, "用户信息不存在");
        }
        if (detail.getDeleted() == Whether.Yes) {
            return R.set(ResultStatus.Unauthorized, "用户信息错误");
        }

        SystemUserInfo systemUserInfo = new SystemUserInfo();
        systemUserInfo.setUserId(detail.getUserId());
        systemUserInfo.setUserName(detail.getUserName());
        systemUserInfo.setAvatar(detail.getAvatar());
        systemUserInfo.setRole(detail.getRole());
        systemUserInfo.setNeedUpdatePwd(detail.getNeedUpdatePwd());

        InbyteSystemUserPo platformPo = InbyteSystemUserPo.builder()
                .userId(detail.getUserId())
                .latestLoginTime(LocalDateTime.now())
                .loginWay(sessionUser.getLoginWay())
                .build();
        inbyteSystemUserMapper.updateById(platformPo);
        return R.ok(systemUserInfo);
    }

    @Override
    public R<List<Dict>> dict(String keyword) {
        return R.ok(inbyteSystemUserMapper.dict(keyword));
    }

    @Override
    public R insert(SystemUserInsert insert) {
        InbyteSystemUserPo user = inbyteSystemUserMapper.selectOne(new LambdaQueryWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getUserName, insert.getUserName()));
        if (user != null) {
            return R.failure("该用户名已存在");
        }

        user = inbyteSystemUserMapper.selectOne(new LambdaQueryWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getTel, insert.getTel()));
        if (user != null) {
            return R.failure("该手机号已存在");
        }

        InbyteSystemUserPo inbyteSystemUserPo = InbyteSystemUserPo.builder()
                .mctNo(SessionUtil.getMctNo())
                .createTime(LocalDateTime.now())
                .creator(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(insert, inbyteSystemUserPo);
        inbyteSystemUserPo.setPwd(MD5Util.md5(insert.getPwd()));
        inbyteSystemUserPo.setNeedUpdatePwd(1);
        inbyteSystemUserPo.setNickname(insert.getUserName());
        inbyteSystemUserPo.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        inbyteSystemUserMapper.insert(inbyteSystemUserPo);
        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer userId) {
        LambdaQueryWrapper<InbyteSystemUserPo> queryWrapper = new LambdaQueryWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getUserId, userId)
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getMctNo());
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
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getMctNo());
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
        query.setMctNo(SessionUtil.getMctNo());
        return R.page(inbyteSystemUserMapper.list(query));
    }

    @Override
    public R updatePwd(SystemUserPwdUpdate update) {
        LambdaUpdateWrapper<InbyteSystemUserPo> updateWrapper = new LambdaUpdateWrapper<InbyteSystemUserPo>()
                .eq(InbyteSystemUserPo::getUserId, SessionUtil.getUserId())
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getMctNo())
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
                .eq(InbyteSystemUserPo::getMctNo, SessionUtil.getMctNo())
                .set(InbyteSystemUserPo::getPwd, Initial_Password)
                .set(InbyteSystemUserPo::getNeedUpdatePwd, WhetherDict.Yes.code)
                .set(InbyteSystemUserPo::getModifier, SessionUtil.getUserName())
                .set(InbyteSystemUserPo::getUpdateTime, LocalDateTime.now());
        inbyteSystemUserMapper.update(updateWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R switchMerchant(String mctNo) {
        SessionUser sessionUser = SessionUtil.getSessionUser();

        List<InbyteSystemUserMerchantBrief> merchantBriefList = inbyteSystemUserMerchantMapper.listByUserId(sessionUser.getUserId());
        if (!merchantBriefList.stream().anyMatch(item -> item.getMctNo().equals(mctNo))) {
            return R.failure("当前用户没有对应商户权限, 切换失败");
        }
        InbyteMerchantPo inbyteMerchantPo = inbyteMerchantMapper.selectById(mctNo);
        if (inbyteMerchantPo == null) {
            return R.failure("商户不存在");
        }
        sessionUser.setMctNo(mctNo);
        sessionUser.setMctName(inbyteMerchantPo.getMctName());
        sessionUser.setMctPinYinName(inbyteMerchantPo.getPinyinName());

        return R.ok("登录成功", SessionUtil.getJwtToken(sessionUser));
    }

    @Override
    public R<List<InbyteSystemUserMerchantBrief>> merchantList() {
        return R.ok(inbyteSystemUserMerchantMapper.listByUserId(SessionUtil.getUserId()));
    }
}
