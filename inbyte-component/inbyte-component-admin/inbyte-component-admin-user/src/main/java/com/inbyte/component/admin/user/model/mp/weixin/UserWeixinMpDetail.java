package com.inbyte.component.admin.user.model.mp.weixin;

import com.inbyte.component.common.dict.convert.DictSerialize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 微信小程序用户实体
 *
 * @author chenjw
 * @date 2023-08-08 15:07:05
 **/
@Getter
@Setter
public class UserWeixinMpDetail {

    /**
     * 外部用户ID
     */
    private Integer eid;

    /**
     * 小程序 openId
     */
    private String openId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 微信 unionId 多应用唯一
     */
    private String unionId;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 区号
     */
    private String countryCode;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 会话密钥
     */
    private String sessionKey;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最近登录时间
     */
    private LocalDateTime latestLoginTime;

    /**
     * 最近经度
     */
    private BigDecimal longitude;

    /**
     * 最近纬度
     */
    private BigDecimal latitude;

    /**
     * 位置更新时间
     */
    private LocalDateTime locationUpdateTime;

    /**
     * 注册时经度
     */
    private BigDecimal registerLongitude;

    /**
     * 注册时纬度
     */
    private BigDecimal registerLatitude;

    /**
     * 推荐外部用户ID
     */
    private Integer recommendEid;

    /**
     * 已绑定用户
     */
    @DictSerialize
    private Integer boundWithUser;

    /**
     * 绑定用户时间
     */
    private LocalDateTime boundWithUserTime;

    /**
     * 邀请数量
     */
    private Integer inviteCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 推荐注册类型
     */
    private Integer registerType;

    /**
     * 注册说明
     */
    private String registerRemark;

}
