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

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 用户名
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
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 备注
     */
    private String remark;


}
