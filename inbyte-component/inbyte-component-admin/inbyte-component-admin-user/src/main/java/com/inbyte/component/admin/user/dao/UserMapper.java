package com.inbyte.component.admin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.user.model.*;

import java.util.List;

/**
 * 用户
 *
 * 表名：  user
 * @author chenjw
 * @date 2023-02-02 13:13:15
 */
public interface UserMapper extends BaseMapper<UserPo> {

    /**
     * 概要
     *
     * @param userId
     * @return UserBrief
     **/
    UserBrief brief(Integer userId);

    /**
     * 概要
     *
     * @param tel
     * @return UserBrief
     **/
    UserBrief briefByTel(String tel);

    /**
     * 详情
     *
     * @param userId
     * @return UserDetail
     **/
    UserDetail detail(Integer userId);

    /**
     * 查询列表
     *
     * @return List<UserBrief>
     **/
    List<UserBrief> list(UserQuery query);

    /**
     * 查询列表
     *
     * @return List<UserBrief>
     **/
    List<UserDictBrief> dict(UserQuery query);

}
