package com.inbyte.component.admin.user.model;

import com.inbyte.commons.util.convert.Sensitive;
import com.inbyte.commons.util.convert.SensitiveStrategy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户摘要
 *
 * @author chenjw
 * @date 2023-02-02 13:13:15
 **/
@Getter
@Setter
public class UserBrief {

    /** 用户ID */
    private Integer userId;

    /** 手机号 */
    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String tel;

    /** 用户名 */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 真实姓名 */
    private String realName;

    /** 头像 */
    private String avatar;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最近登录时间 */
    private LocalDateTime latestLoginTime;

    /** 推荐人ID */
    private Integer recommendEid;

    /** 推荐方式 */
    private Integer recommendWay;

    /** 备注 */
    private String remark;

    /** 邀请数量 */
    private Integer inviteCount;

}
