package com.inbyte.component.admin.user.model;

import com.inbyte.commons.util.serialize.Sensitive;
import com.inbyte.commons.util.serialize.SensitiveStrategy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户详情
 *
 * @author chenjw
 * @date 2023-02-02 13:13:15
 **/
@Getter
@Setter
public class UserDetail {

    /** 用户ID */
    private Integer userId;

    /** 手机号 */
    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String tel;

    /** 昵称 */
    private String userName;

    /** 昵称 */
    private String nickname;

    /** 真实姓名 */
    private String realName;

    /** 头像 */
    private String avatar;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 最近登录时间 */
    private LocalDateTime latestLoginTime;

    /** 注册客户端 */
    private Integer registerDeviceType;

    /** 推荐人ID */
    private Integer recommendEid;

    /** 推荐方式 */
    private Integer recommendWay;

    /** 备注 */
    private String remark;

    /** 邀请数量 */
    private Integer inviteCount;

}
