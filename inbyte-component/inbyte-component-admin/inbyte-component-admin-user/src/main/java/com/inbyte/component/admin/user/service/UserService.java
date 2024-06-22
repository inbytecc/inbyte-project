package com.inbyte.component.admin.user.service;

import com.inbyte.component.admin.user.model.*;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;

/**
 * 用户服务
 *
 * @author chenjw
 * @date 2023-02-02 13:13:15
 **/
public interface UserService {

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(UserUpdate update);

    /**
     * 详情
     *
     * @param userId
     * @return UserDetail
     **/
    R<UserDetail> detail(Integer userId);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<UserBrief>>>
     **/
    R<Page<UserBrief>> list(UserQuery query);

    /**
     * 微信小程序用户字典
     * @return
     */
    R<Page<UserDictBrief>> dict(UserQuery query);

    /**
     * 手机号注册账号
     *
     * @param tel
     * @return Result
     **/
    Integer register(String tel);

}
