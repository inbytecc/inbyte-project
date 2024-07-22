package com.inbyte.component.app.user.framework;

import com.inbyte.commons.model.enums.AppTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 会话对象
 *
 * @author chenjw
 * @date 2020/8/6
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SessionUser {

    /**
     * 用户ID
     * 当前用户注册绑定手机号时, 有 userId 字段
     */
    private Integer userId;

    /**
     * 外部用户ID
     */
    private Integer eid;
    /**
     * 外部用户OpenID
     */
    private String openId;

    /**
     * 外部用户类型
     */
    private AppTypeEnum appType;

    /**
     * 手机号, 不可靠
     * 仅限日志记录相关使用, 因为用户修改名字后没有做统一更新
     */
    private String tel;

    /**
     * 已绑定手机号
     */
    private Integer telBound;

    /**
     * 用户名, 不可靠
     * 仅限日志记录相关使用, 因为用户修改名字后没有做统一更新
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * token版本
     * 用于全局校验, 极端情况可让全平台旧版本用户重新登录
     */
    private Double tokenVersion;

    /**
     * 推荐用户EID
     */
    private Integer recommendEid;

}
