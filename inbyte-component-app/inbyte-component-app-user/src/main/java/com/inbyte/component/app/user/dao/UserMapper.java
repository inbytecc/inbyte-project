package com.inbyte.component.app.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.user.model.UserBrief;
import com.inbyte.component.app.user.model.location.UserLocationPo;
import com.inbyte.component.app.user.model.UserPo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 user表
 *
 * @author chenjw
 * @date 2022-10-20 17:16:36
 */
public interface UserMapper extends BaseMapper<UserPo> {

    /**
     * 手机号查询用户
     *
     * @param tel
     * @return 用户session信息
     */
    UserBrief queryByTel(@Param("tel") String tel);

    /**
     * 全字段新增
     *
     * @param insert
     * @return 新增条数
     **/
    int insertSelective(UserPo insert);

    /**
     * 插入用户位置
     * 二小时内不重复记录
     *
     * @param userLocationPo
     * @return
     */
    int insertLocationSelective(UserLocationPo userLocationPo);

}