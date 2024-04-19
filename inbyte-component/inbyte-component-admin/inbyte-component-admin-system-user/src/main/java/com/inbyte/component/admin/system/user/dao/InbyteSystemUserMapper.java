package com.inbyte.component.admin.system.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.user.model.system.user.*;
import com.inbyte.commons.model.dto.Dict;

import java.util.List;

/**
 * 用户表
 *
 * 表名：  inbyte_system_user
 * @author chenjw
 * @date 2022-12-20 14:58:26
 */
public interface InbyteSystemUserMapper extends BaseMapper<InbyteSystemUserPo> {

    /**
     * 详情
     *
     * @param id
     * @return UserPlatform
     **/
    SystemUserDetail detail(Integer id);

    /**
     * 详情
     *
     * @param param
     * @return UserPlatform
     **/
    SystemUserDetail queryByPwd(SystemUserLoginParam param);

    /**
     * 详情
     *
     * @param openId
     * @return UserPlatform
     **/
    SystemUserDetail queryByOpenId(String openId);

    /**
     * 详情
     *
     * @param tel
     * @return UserPlatform
     **/
    SystemUserDetail queryByTel(String tel);

    /**
     * 查询列表
     *
     * @return UserPlatform
     **/
    List<SystemUserBrief> list(SystemUserQuery query);

    List<Dict> dict(String keyword);
}