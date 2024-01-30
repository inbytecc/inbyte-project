package com.inbyte.component.app.user.framework;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inbyte.component.app.user.framework.exception.AppSessionUnavailableException;
import com.inbyte.component.app.user.framework.exception.AppSessionUnregisteredException;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.commons.util.WebUtil;

/**
 * Session 工具
 *
 * @author: chenjw
 * @date: 2020/11/24
 */
public class SessionUtil {

    private static final String Authorization = "Authorization";
    public static final Double User_Token_Version = 1.3;

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static Integer getUserId() {
        Integer userId = getSessionUser().getUserId();
        if (userId == null) {
            throw new AppSessionUnregisteredException();
        }
        return userId;
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static Integer getEid() {
        Integer eid = getSessionUser().getEid();
        if (eid == null) {
            throw new AppSessionUnavailableException();
        }
        return eid;
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static String getUserName() {
        String nickName = getSessionUser().getNickName();
        return StringUtil.isEmpty(nickName) ? "" : nickName;
    }

    /**
     * 获取用户对象, HTTP接口请求使用
     * 获取Authorization请求头, 解析JSON WEB TOKEN信息
     *
     * @return
     */
    public static SessionUser getSessionUser() {
        String authorization = WebUtil.getHeader(Authorization);
        if (authorization == null) {
            throw new AppSessionUnavailableException();
        }
        SessionUser sessionUser = UserJwtUtil.parseObject(authorization, SessionUser.class);
        if (sessionUser.getTokenVersion() < User_Token_Version) {
            throw new AppSessionUnavailableException();
        }

        return sessionUser;
    }

    public static SessionUser getSessionUserUnchecked() {
        String authorization = WebUtil.getHeader(Authorization);
        if (authorization == null) {
            return null;
        }
        DecodedJWT decodedJWT = UserJwtUtil.verifierToken(authorization);
        if (decodedJWT == null) {
            return null;
        }
        return JSON.parseObject(decodedJWT.getSubject(), SessionUser.class);
    }

    /**
     * 使用SessionUser转换jwt token
     *
     * @param sessionUser
     * @return
     */
    public static String getJwtToken(SessionUser sessionUser) {
        sessionUser.setTokenVersion(User_Token_Version);
        return UserJwtUtil.createJwt(sessionUser);
    }

    /**
     * 已登录
     *
     * 静默登录也算
     * @return
     */
    public static boolean silentLogged() {
        return getSessionUserUnchecked() != null ? true : false;
    }

    /**
     * 已登录
     *
     * 静默登录不算，需要绑定手机号
     * @return
     */
    public static boolean logged() {
        SessionUser sessionUserUnchecked = getSessionUserUnchecked();
        if (sessionUserUnchecked == null) {
            return false;
        }
        if (sessionUserUnchecked.getTelBound() == null || sessionUserUnchecked.getTelBound() == 0) {
            return false;
        }
        return true;
    }

}
