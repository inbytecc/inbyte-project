package com.inbyte.component.admin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.user.model.data.UserTrendQuery;
import com.inbyte.component.admin.user.model.mp.UserTrendBrief;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpBrief;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpDetail;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpPo;
import com.inbyte.component.admin.user.model.mp.weixin.UserWeixinMpQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("SELECT COUNT(*)" +
            "  FROM user_weixin_mp" +
            " WHERE deleted = 0" +
            "   AND mct_no = #{mctNo}")
    int getTotalUsersEndOfDay(String mctNo);

    @Select("SELECT COUNT(*)" +
            "  FROM user_weixin_mp" +
            " WHERE DATE(create_time) < CURDATE()" +
            "   AND deleted = 0 AND mct_no = #{mctNo}")
    int getTotalUsersStartOfDay(String mctNo);

    @Select("SELECT COUNT(*)" +
            "  FROM user_weixin_mp" +
            " WHERE deleted = 0" +
            "   AND mct_no = #{mctNo}")
    int getTotalUsersEndOfWeek(String mctNo);

    @Select("SELECT COUNT(*)" +
            "  FROM user_weixin_mp" +
            " WHERE YEARWEEK(create_time, 1) < YEARWEEK(CURDATE(), 1)" +
            "   AND deleted = 0" +
            "   AND mct_no = #{mctNo}")
    int getTotalUsersStartOfWeek(String mctNo);

    @Select("SELECT COUNT(*) FROM user_weixin_mp WHERE deleted = 0 AND mct_no = #{mctNo}")
    int getTotalUsersEndOfMonth();

    @Select("SELECT COUNT(*) FROM user_weixin_mp" +
            " WHERE YEAR(create_time) < YEAR(CURDATE()) OR (YEAR(create_time) = YEAR(CURDATE()) " +
            "   AND MONTH(create_time) < MONTH(CURDATE()))" +
            "   AND deleted = 0" +
            "   AND mct_no = #{mctNo}")
    int getTotalUsersStartOfMonth(String mctNo);
}
