package com.inbyte.component.admin.user.model.mp.weixin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.model.enums.RecommendTypeEnum;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("user_weixin_mp")
public class UserWeixinMpPo {

    /**
     * 外部用户ID
     */
    @TableId(value = "eid", type = IdType.AUTO)
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
      * 小程序客户端appId
      */
    private String appId;

    /**
      * 手机号
      */
    private String tel;

    /**
      * 密码MD5
      */
    private String pwd;

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
      * 推荐用户ID
      */
    private Integer recommendEid;

    /**
      * 已绑定用户
      */
    private Integer boundWithUser;

    /**
      * 绑定用户时间
      */
    private LocalDateTime boundWithUserTime;

    /**
      * 逻辑删除
      */
    private Integer deleted;

    /**
      * 邀请数量
      */
    private Integer inviteCount;

    private String mctNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 推荐注册类型
     */
    private RecommendTypeEnum recommendType;

    /**
     * 注册说明
     */
    private String registerRemark;
}
