package com.inbyte.component.admin.user.model.mp.alipay;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付宝小程序用户实体
 *
 * @author chenjw
 * @date 2023-10-12 09:36:51
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("user_alipay_mp")
public class UserAlipayMpPo {

    /**
      * 外部用户ID
      */
    @TableId(value = "eid", type = IdType.AUTO)
    private Integer eid;

    /**
      * 支付宝openId
      */
    private String alipayOpenId;

    /**
      * 支付宝unionId
      */
    private String alipayUnionId;

    /**
      * 用户ID
      */
    private Integer userId;

    /**
      * 昵称
      */
    private String nickname;

    /**
      * 性别
      */
    private Integer gender;

    /**
      * 小程序客户端appId
      */
    private String appId;

    /**
      * 商户号
      */
    private String mctNo;

    /**
      * 手机号
      */
    private String tel;

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
      * 已绑定用户
      */
    private Integer boundWithUser;

    /**
      * 绑定用户时间
      */
    private LocalDateTime boundWithUserTime;

    /**
      * 登录次数
      */
    private Integer loginCount;

    /**
      * 逻辑删除
      */
    private Integer deleted;

    /**
      * 备注
      */
    private String remark;

}
