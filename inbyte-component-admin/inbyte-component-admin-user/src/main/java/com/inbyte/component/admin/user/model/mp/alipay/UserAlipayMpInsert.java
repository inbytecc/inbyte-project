package com.inbyte.component.admin.user.model.mp.alipay;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付宝小程序用户创建
 *
 * @author chenjw
 * @date 2023-10-12 09:36:51
 **/
@Getter
@Setter
public class UserAlipayMpInsert {

    /** 支付宝openId */
    @NotNull(message = "支付宝openId不能为空")
    @Length(max = 64, message = "支付宝openId长度不能超过64位")
    private String alipayOpenId;

    /** 支付宝unionId */
    @Length(max = 32, message = "支付宝unionId长度不能超过32位")
    private String alipayUnionId;

    /** 用户ID */
    private Integer userId;

    /** 昵称 */
    @Length(max = 16, message = "昵称长度不能超过16位")
    private String nickName;

    /** 性别 */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /** 小程序客户端appId */
    @NotNull(message = "小程序客户端appId不能为空")
    @Length(max = 64, message = "小程序客户端appId长度不能超过64位")
    private String appId;

    /** 商户号 */
    @NotNull(message = "商户号不能为空")
    @Length(max = 32, message = "商户号长度不能超过32位")
    private String mctNo;

    /** 手机号 */
    @Length(max = 11, message = "手机号长度不能超过11位")
    private String tel;

    /** 头像 */
    @Length(max = 256, message = "头像长度不能超过256位")
    private String avatar;

    /** 最近登录时间 */
    private LocalDateTime latestLoginTime;

    /** 最近经度 */
    private BigDecimal longitude;

    /** 最近纬度 */
    private BigDecimal latitude;

    /** 位置更新时间 */
    private LocalDateTime locationUpdateTime;

    /** 注册时经度 */
    private BigDecimal registerLongitude;

    /** 注册时纬度 */
    private BigDecimal registerLatitude;

    /** 已绑定用户 */
    @NotNull(message = "已绑定用户不能为空")
    private Integer boundWithUser;

    /** 绑定用户时间 */
    private LocalDateTime boundWithUserTime;

    /** 登录次数 */
    @NotNull(message = "登录次数不能为空")
    private Integer loginCount;

    /** 备注 */
    @Length(max = 255, message = "备注长度不能超过255位")
    private String remark;

}
