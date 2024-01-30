package com.inbyte.component.app.user.model;

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

    /** 用户名 */
    private String userName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}