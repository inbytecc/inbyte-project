package com.inbyte.component.app.user.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户详情
 *
 * @author chenjw
 * @date 2022-10-20 17:16:36
 **/
@Getter
@Setter
public class UserWeixinDetail {
    /**
     * 外部用户ID
     */
    private Integer eid;
    /**
     * 微信用户openId
     */
    private String openId;
    /**
     * 微信用户unionId
     */
    private String unionId;
    /**
     * 微信用户小程序appId
     */
    private String appId;
    /**
     * 已绑定用户
     */
    private Integer boundWithUser;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像URL
     */
    private String avatar;
    /**
     * 位置更新时间
     */
    private LocalDateTime locationUpdateTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}