package com.inbyte.component.app.user.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 邀请用户
 *
 * @author chenjw
 * @date 2022-10-20 17:16:36
 **/
@Getter
@Setter
public class UserInviteDto {

    /** 昵称 */
    private String nickName;


    /** 已预约 */
    private Integer appointed;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}