package com.inbyte.component.admin.system.user;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.util.WebUtil;
import com.inbyte.component.admin.system.user.exception.AdminUnavailableException;
import com.inbyte.component.admin.system.user.exception.SystemUserSessionUnavailableException;

/**
 * Session工具
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
            throw new SystemUserSessionUnavailableException();
        }
        return userId;
    }

    /**
     * 获取当前用户商户ID
     *
     * @return
     */
    public static String getMctNo() {
        String defaultMctNo = getSessionUser().getMctNo();
        if (defaultMctNo == null) {
            throw new SystemUserSessionUnavailableException();
        }
        return defaultMctNo;
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static String getUserName() {
        return getSessionUser().getUserName();
    }

    /**
     * 获取用户对象, HTTP接口请求使用
     * 获取ZMY-Authorization请求头, 解析JSON WEB TOKEN信息
     *
     * @return
     */
    public static SessionUser getSessionUser() {
        String authorization = WebUtil.getHeader(Authorization);
        if (authorization == null) {
            throw new SystemUserSessionUnavailableException();
        }

        SessionUser sessionUser = SystemUserJwtUtil.parseObject(authorization, SessionUser.class);
        if (sessionUser.getTokenVersion() < User_Token_Version) {
            throw new SystemUserSessionUnavailableException();
        }
        return sessionUser;
    }

    public static SessionUser getSessionUserUnchecked() {
        String authorization = WebUtil.getHeader(Authorization);
        if (authorization == null) {
            return null;
        }
        DecodedJWT decodedJWT = SystemUserJwtUtil.verifierToken(authorization);
        if (decodedJWT == null) {
            return null;
        }
        return JSON.parseObject(decodedJWT.getSubject(), SessionUser.class);
    }

//    public static String getVenueId() {
//        String venueId = WebUtil.getHeader("Current-Venue-Id");
//        if (venueId == null) {
//            throw InbyteException.failure("请选择营地");
//        }
//        return venueId;
//    }

    /**
     * 使用SessionUser转换jwt token
     *
     * @param sessionUser
     * @return
     */
    public static String getJwtToken(SessionUser sessionUser) {
        sessionUser.setTokenVersion(User_Token_Version);
        return SystemUserJwtUtil.createJwt(sessionUser);
    }

    /**
     * 已登录
     *
     * 静默登录不算
     * @return
     */
    public static boolean logged() {
        SessionUser sessionUser = getSessionUserUnchecked();
        return sessionUser != null;
    }

    /**
     * 已登录
     *
     * 静默登录不算
     * @return
     */
    public static void assertAdmin() {
        SessionUser sessionUser = getSessionUser();
        if (sessionUser.getAdmin() == WhetherDict.Yes.code) {
            throw new AdminUnavailableException();
        }
    }


}
