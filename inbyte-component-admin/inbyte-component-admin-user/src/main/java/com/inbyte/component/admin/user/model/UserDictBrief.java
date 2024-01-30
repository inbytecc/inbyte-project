package com.inbyte.component.admin.user.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户字典摘要
 *
 * @author chenjw
 * @date 2023-02-02 13:13:15
 **/
@Getter
@Setter
public class UserDictBrief {

    /** 用户ID */
    private Integer userId;

    /** 手机号 */
    private String tel;

    /** 昵称 */
    private String nickName;

    /** 头像 */
    private String avatarUrl;

    /** 创建时间 */
    private LocalDateTime createTime;

}
