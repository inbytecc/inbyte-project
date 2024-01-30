//package com.pyrange.component.system.user.logging;
//
//import com.alibaba.fastjson2.JSON;
//import com.pyrange.common.util.IpUtil;
//import com.pyrange.common.util.WebUtil;
//import com.pyrange.component.system.user.SessionUser;
//import com.pyrange.component.system.user.SessionUtil;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//
///**
// * @author: chenjw
// * @date: 2018/4/28 下午5:51
// * @Description:
// */
//public class SystemUserLogUtil {
//
//    private static final Logger Access_Logger = LoggerFactory.getLogger("后台系统访问日志");
//    private static final Logger Return_Logger = LoggerFactory.getLogger("后台系统接请求结果");
//    private static final String ERROR = "error";
//
//    private static final String GET = "get";
//    private static final String USER_AGENT_HEADER = "user-agent";
//    private static final String EMPTY_JSON = "{}";
//    private static final String DEFAULT_VALUE = "-";
//
//    /**
//     * 注意每个字段前加1个空格, 用于Logstash解析
//     */
//    private static final String USER_ID = "userId:";
//    private static final String USER_NAME = " userName:";
//    private static final String WAN_IP = " wanIp:";
//    private static final String LAN_IP = " lanIp:";
//    private static final String URL = " url:";
//    private static final String REQUEST_METHOD = " requestMethod:";
//    private static final String QUERY_STR = " queryStr:";
//    private static final String FORM_PARAM = " formParam:";
//    private static final String REQUEST_BODY = " requestBody:";
//    private static final String USER_AGENT = " userAgent:";
//
//
//    /**
//     * 访问日志
//     * Logback前置格式: %date日志 env:环境 level:日志级别 logId:日志ID logger:日志类
//     * 格式：clientIp:客户端IP url:请求URL requestMethod:请求方法 queryStr:查询字符串 requestBody:JSON参数 userAgent:客户端代理
//     * userId:用户ID csId:当前店铺 os:手机系统 osVersion:系统版本 model:手机型号 version:App版本 imei:设备唯一标识码 channel: 下载渠道
//     */
//    public static String getRequestInfo() {
//        return getRequestInfo(null);
//    }
//
//    public static String getRequestInfo(JoinPoint joinPoint) {
//        HttpServletRequest request = WebUtil.getCurrentRequest();
//        StringBuilder sb = new StringBuilder();
//        SessionUser sessionUserUnchecked = SessionUtil.getSessionUserUnchecked();
//        if (sessionUserUnchecked != null) {
//            sb.append(USER_ID).append(sessionUserUnchecked.getUserId());
//            sb.append(USER_NAME).append(sessionUserUnchecked.getUserName());
//        } else {
//            sb.append(USER_ID).append("-");
//            sb.append(USER_NAME).append("-");
//        }
//        sb.append(WAN_IP).append(IpUtil.getWanIpAddress());
//        sb.append(LAN_IP).append(IpUtil.getLanIpAddress());
//        String requestUri = request.getRequestURI();
//        if (requestUri.contains(ERROR)) {
//            requestUri = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI).toString();
//        }
//        sb.append(URL).append(requestUri);
//        sb.append(REQUEST_METHOD).append(request.getMethod());
//        sb.append(QUERY_STR).append(defaultIfEmpty(request.getQueryString(), DEFAULT_VALUE));
//        sb.append(FORM_PARAM).append(defaultIfEmpty(JSON.toJSONString(request.getParameterMap()), EMPTY_JSON));
//        sb.append(REQUEST_BODY).append(getJsonParam(request, joinPoint));
//        sb.append(USER_AGENT).append(request.getHeader(USER_AGENT_HEADER));
//
//        return sb.toString();
//    }
//
//    /**
//     * 获取RequestBody参数
//     *
//     * @param request
//     * @param joinPoint
//     * @return
//     */
//    private static String getJsonParam(HttpServletRequest request, JoinPoint joinPoint) {
//        if (joinPoint == null) {
//            return EMPTY_JSON;
//        }
//        boolean nonJsonParam = GET.equals(request.getMethod()) ? true : false;
//        if (nonJsonParam) {
//            return EMPTY_JSON;
//        }
//        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
//        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//        for (int i = 0; i < parameterAnnotations.length; i++) {
//            for (int j = 0; j < parameterAnnotations[i].length; j++) {
//                if (parameterAnnotations[i][j] instanceof RequestBody) {
//                    return JSON.toJSONString(joinPoint.getArgs()[i]);
//                }
//            }
//        }
//        return EMPTY_JSON;
//    }
//
//    public static void access(JoinPoint joinPoint) {
//        String requestInfo = getRequestInfo(joinPoint);
//        if (requestInfo != null) {
//            Access_Logger.info(requestInfo);
//        }
//    }
//
//    private static String defaultIfEmpty(String str, String defaultValue) {
//        return str == null || "".equals(str) ? defaultValue : str;
//    }
//
//
//    /**
//     * 返回结果日志输出
//     *
//     * @param returnValue
//     */
//    public static void AfterReturning(Object returnValue) {
//        Return_Logger.info("url:{}, result:{}", WebUtil.getRequestUri(), JSON.toJSONString(returnValue) );
//    }
//}
