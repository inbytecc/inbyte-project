package com.inbyte.component.admin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.user.model.data.UserTrendQuery;
import com.inbyte.component.admin.user.model.mp.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信小程序用户
 * <p>
 * 表名：  user_weixin_mp
 *
 * @author chenjw
 * @date 2023-03-29 13:58:35
 */
public interface UserWeixinMpMapper extends BaseMapper<UserWeixinMpPo> {

    /**
     * 详情
     *
     * @param eid
     * @return UserWeixinMpDetail
     **/
    UserWeixinMpDetail detail(@Param("eid") Integer eid,
                              @Param("mctNo") String mctNo);

    /**
     * 详情
     *
     * @param userId
     * @return UserWeixinMpDetail
     **/
    UserWeixinMpDetail getByUserId(@Param("userId") Integer userId,
                                   @Param("mctNo") String mctNo);

    /**
     * 查询列表
     *
     * @param query
     * @return List<UserWeixinMpBrief>
     **/
    List<UserWeixinMpBrief> list(UserWeixinMpQuery query);

    /**
     * 用户增长趋势
     *
     * @param query
     * @return
     */
    List<UserTrendBrief> userTrend(UserTrendQuery query);
}
