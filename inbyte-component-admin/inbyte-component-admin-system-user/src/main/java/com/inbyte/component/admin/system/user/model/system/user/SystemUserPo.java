package com.inbyte.component.admin.system.user.model.system.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.inbyte.commons.util.convert.ListTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台用户实体
 *
 * @author chenjw
 * @date 2023-05-23 12:00:10
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("system_user")
public class SystemUserPo {

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 账户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 密码MD5
     */
    private String pwd;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信用户开放ID
     */
    private String openId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建人名字
     */
    private String createUserName;

    /**
     * 创建人ID
     */
    private Integer updateUserId;

    /**
     * 修改人名字
     */
    private String updateUserName;

    /**
     * 创建时间
     */
    private LocalDateTime updateTime;

    /**
     * 最近登录时间
     */
    private LocalDateTime latestLoginTime;

    /**
     * 禁用
     */
    private Integer disabled;

    /**
     * 角色
     */
    @TableField(typeHandler = ListTypeHandler.class)
    private List<Integer> role;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 超管
     */
    private Integer admin;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登录方式
     */
    private String loginWay;

    /**
     * 需要修改密码
     */
    private Integer needUpdatePwd;

    /**
     * 默认商户号
     */
    private String mctNo;
}
