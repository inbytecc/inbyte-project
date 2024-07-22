package com.inbyte.component.admin.system.user.model.system.user;

import com.alibaba.fastjson2.JSONArray;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户表摘要
 *
 * @author chenjw
 * @date 2022-12-20 14:58:26
 **/
@Getter
@Setter
public class SystemUserBrief {

    /** 用户ID */
    private Integer userId;

    /** 账户名 */
    private String userName;

    /** 昵称 */
    private String nickname;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private String tel;

    /** 密码MD5 */
    private String pwd;

    /** 头像 */
    private String avatar;

    /** 邮箱 */
    private String email;

    /** 禁用 */
    private Integer disabled;

    /** 角色描述 */
    private String roleDesc;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最近登录时间 */
    private LocalDateTime latestLoginTime;

    /** 备注 */
    private String remark;

    /** 需要修改密码 */
    private Integer needUpdatePwd;
}