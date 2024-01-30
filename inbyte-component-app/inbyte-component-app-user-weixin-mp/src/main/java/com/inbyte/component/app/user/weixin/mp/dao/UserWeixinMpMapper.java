package com.inbyte.component.app.user.weixin.mp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinDetail;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinDto;
import com.inbyte.component.app.user.weixin.mp.model.UserWeixinMpPo;
import org.apache.ibatis.annotations.Param;

/**
 * 微信用户表
 * <p>
 * 表名：  user_weixin
 *
 * @author chenjw
 * @date 2022-10-20 16:08:07
 */
public interface UserWeixinMpMapper extends BaseMapper<UserWeixinMpPo> {

    /**
     * 手机号查询用户
     *
     * @param openId
     * @return 用户session信息
     */
    UserWeixinDetail detail(@Param("openId") String openId);

    /**
     * 根据主键查询
     *
     * @param eid
     * @return User
     **/
    UserWeixinDto info(@Param("eid") Integer eid);

}