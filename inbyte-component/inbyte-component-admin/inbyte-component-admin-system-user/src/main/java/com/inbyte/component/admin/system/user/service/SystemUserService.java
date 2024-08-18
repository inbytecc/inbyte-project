package com.inbyte.component.admin.system.user.service;

import com.inbyte.component.admin.system.user.model.system.user.*;
import com.inbyte.commons.model.dto.Dict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;

import java.util.List;

/**
 * 用户表服务
 *
 * @author : chenjw
 * @date: 2022/11/01
 **/
public interface SystemUserService {

    /**
     * 手机号登录
     *
     * 手机号加密码登录
     * @param param
     * @return
     */
    R<SystemUserLoginDto> idPwdLogin(SystemUserLoginParam param);

    /**
     * 登录用户信息
     * @return
     */
    R<SystemUserInfo> info();

    /**
     * 平台用户字典
     * @param keyword
     * @return
     */
    R<List<Dict>> dict(String keyword);

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    R insert(SystemUserInsert insert);

    /**
     * 删除
     *
     * @param userId
     * @return Result
     **/
    R delete(Integer userId);

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(SystemUserUpdate update);

    /**
     * 详情
     *
     * @param userId
     * @return UserPlatformDetail
     **/
    R<SystemUserDetail> detail(Integer userId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<UserPlatformBrief>>>
     **/
    R<Page<SystemUserBrief>> list(SystemUserQuery query);

    R updatePwd(SystemUserPwdUpdate update);

    R resetPwd(Integer userId);

    R switchMerchant(String mctNo);
}
