package com.inbyte.component.app.user.weixin.mp.model.invite;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户邀请实体
 *
 * @author chenjw
 * @date 2023-03-29 16:30:28
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("user_weixin_mp_invite")
public class UserWeixinMpInvitePo {

    /**
      * 邀请注册ID
      */
    @TableId(value = "invite_id", type = IdType.AUTO)
    private Integer inviteId;

    /**
      * 注册用户ID
      */
    private Integer eid;

    /**
      * 邀请人ID
      */
    private Integer referrerEid;

    /**
      * 创建时间
      */
    private LocalDateTime createTime;

    /**
     * 已注册
     */
    private Integer registered;

    /**
     * 已预约
     */
    private Integer appointed;

    /**
     * 已购买
     */
    private Integer purchased;

    /**
     * 购买次数
     */
    private Integer purchaseCount;

    /**
     * 购买总金额
     */
    private BigDecimal tradeAmount;

    /**
     * 购买时间
     */
    private Integer purchaseTime;

}
