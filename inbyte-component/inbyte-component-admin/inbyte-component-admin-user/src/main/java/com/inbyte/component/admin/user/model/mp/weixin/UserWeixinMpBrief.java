package com.inbyte.component.admin.user.model.mp.weixin;

import com.inbyte.commons.model.enums.RecommendTypeEnum;
import com.inbyte.commons.util.serialize.Sensitive;
import com.inbyte.commons.util.serialize.SensitiveStrategy;
import com.inbyte.component.common.dict.convert.DictSerialize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 微信小程序用户实体
 *
 * @author chenjw
 * @date 2023-03-29 13:58:35
 **/
@Getter
@Setter
public class UserWeixinMpBrief {

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
     * 手机号
     */
    @Sensitive(strategy = SensitiveStrategy.PHONE)
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
     * 商户推荐码
     */
    private Integer qcid;

    /**
     * 邀请数
     */
    private Integer inviteCount;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 推荐注册类型
     */
    @DictSerialize(RecommendTypeEnum.class)
    private RecommendTypeEnum recommendType;

    /**
     * 注册说明
     */
    private String registerRemark;
}
