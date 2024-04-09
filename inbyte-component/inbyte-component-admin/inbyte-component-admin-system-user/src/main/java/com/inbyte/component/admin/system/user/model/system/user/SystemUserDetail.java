package com.inbyte.component.admin.system.user.model.system.user;

import com.alibaba.fastjson2.JSONArray;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户表详情
 *
 * @author chenjw
 * @date 2022-12-20 14:58:26
 **/
@Getter
@Setter
public class SystemUserDetail {

    /** 用户ID */
    private Integer userId;

    /** 昵称 */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private String tel;

    /** 微信用户ID */
    private String openId;

    /** 头像 */
    private String avatar;

    /** 邮箱 */
    private String email;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最近登录时间 */
    private LocalDateTime latestLoginTime;

    /** 逻辑删除 */
    private Integer deleted;

    /** 备注 */
    private String remark;

    /** 禁用 */
    private Integer disabled;

    /** 角色描述 */
    private String roleDesc;

    /** 角色 */
    private JSONArray role;

    /** 需要修改密码 */
    private Integer needUpdatePwd;

    /** 默认商户ID */
    private String mctNo;
    /** 商户名 */
    private String mctName;
    /** 商户名拼音 */
    private String pinYinName;
    /** 超管 */
    private Integer admin;

}