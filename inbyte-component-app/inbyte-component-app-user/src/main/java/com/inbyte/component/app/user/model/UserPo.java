package com.inbyte.component.app.user.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author chenjw
 * @date 2023-03-09 10:47:06
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("user")
public class UserPo {

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
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
     * 密码MD5
     */
    private String pwd;

    /**
     * 头像
     */
    private String avatarUrl;

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

