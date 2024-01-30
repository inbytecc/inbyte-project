package com.inbyte.component.app.user.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户详情
 *
 * @author chenjw
 * @date 2022-10-20 17:16:36
 **/
@Getter
@Setter
public class UserWeixinDto {

    /** 用户ID */
    private Integer userId;

    /** 手机号 */
    private String tel;

    /** 昵称 */
    private String nickName;

    /** 头像 */
    private String avatarUrl;

}