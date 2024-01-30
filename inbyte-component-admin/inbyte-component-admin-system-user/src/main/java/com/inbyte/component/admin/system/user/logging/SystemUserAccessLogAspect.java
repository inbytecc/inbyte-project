//package com.pyrange.component.system.user.logging;
//
//import com.pyrange.common.util.WebUtil;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 平台日志记录
// *
// * @author chenjw
// * @date 2017/11/21 下午8:40
// */
//@Component
//@Aspect
//@Order(1)
//public class SystemUserAccessLogAspect {
//
//    /**
//     * 忽略记录的接口地址
//     */
//    private static final List Ignore_Api = Arrays.asList(
//            "/health",
//            "/error",
//            "/aliyun/oss/callback"
//    );
//
//    @Pointcut("@within(org.springframework.stereotype.Controller) ||" +
//            "@within(org.springframework.web.bind.annotation.RestController)")
//    public void controllerMethod() {
//    }
//
//    /**
//     * 访问日志
//     * Logback前置格式: %date日志 env:环境 level:日志级别 logId:日志ID logger:日志类
//     * 格式：clientIp:客户端IP url:请求URL requestMethod:请求方法 queryStr:查询字符串 requestBody:JSON参数
//     * csId:当前店铺 os:手机系统 osVersion:系统版本 model:手机型号 version:App版本 imei:设备唯一标识码 channel: 下载渠道
//     */
//    @Before("controllerMethod()")
//    public void preHandleAccessLog(JoinPoint joinPoint) {
//        if (ignore()) {
//            return;
//        }
//        SystemUserLogUtil.access(joinPoint);
//    }
//
//    private boolean ignore() {
//        String requestURI = WebUtil.getCurrentRequest().getRequestURI();
//        if (requestURI.matches(".*?/dict/.*")) {
//            return true;
//        }
//        return Ignore_Api.contains(requestURI);
//    }
//
//    /**
//     * 响应结果日志
//     */
//    @AfterReturning(value = "controllerMethod()", returning = "returnValue")
//    public void afterHandleAccessLog(Object returnValue) {
//        if (ignore()) {
//            return;
//        }
//        SystemUserLogUtil.AfterReturning(returnValue);
//    }
//}
