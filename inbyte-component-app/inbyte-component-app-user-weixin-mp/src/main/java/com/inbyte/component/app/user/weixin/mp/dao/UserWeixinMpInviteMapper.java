package com.inbyte.component.app.user.weixin.mp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.app.user.weixin.mp.model.invite.UserWeixinMpInvitePo;
import com.inbyte.component.app.user.weixin.mp.model.UserInviteDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户邀请
 *
 * 表名：  user_weixin_mp_invite
 * @author chenjw
 * @date 2023-03-29 16:30:28
 */
public interface UserWeixinMpInviteMapper extends BaseMapper<UserWeixinMpInvitePo> {

    /**
     * 用户购买记录
     * @param eid
     * @param purchaseTime
     * @param tradeAmount
     */
    @Update("UPDATE user_weixin_mp_invite " +
            "SET purchased = 1, " +
            "purchaseTime = #{purchaseTime}, " +
            "purchaseCount = purchaseCount + 1, " +
            "tradeAmount = tradeAmount + #{tradeAmount} " +
            "WHERE eid = #{eid}")
    void increaseOrder(@Param("eid") Integer eid,
                       @Param("purchaseTime") LocalDateTime purchaseTime,
                       @Param("tradeAmount") BigDecimal tradeAmount);


    List<UserInviteDto> list(Integer recommendEid);

}
