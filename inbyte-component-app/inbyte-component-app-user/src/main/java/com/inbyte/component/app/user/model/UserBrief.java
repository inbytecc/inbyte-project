package com.inbyte.component.app.user.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户摘要
 *
 * @author chenjw
 * @date 2022-10-20 17:16:36
 **/
@Getter
@Setter
public class UserBrief {

    /** 用户ID */
    private Integer userId;

    /** 手机号 */
    private String tel;

    /** 昵称 */
    private String nickName;

    /** 邮箱 */
    private String email;

    /** 头像 */
    private String avatarUrl;

}