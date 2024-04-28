package com.inbyte.component.admin.system.user.aspect;

import com.inbyte.commons.util.WebUtil;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.exception.SystemUserSessionUnavailableException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


/**
 * 用户session拦截校验
 *
 * @author chenjw
 **/
@Aspect
@Component
public class SystemUserSessionCheckAspect {

    /**
     * 忽略记录的接口地址
     */
    private static final List Ignore_Api = Arrays.asList(
            "/health",
            "/error",
            "/aliyun/oss/callback",
            "/api/system/user/login/id-pwd",
            "/api/user/merchant/login/id-pwd",
            "/api/common/district/province",
            "/api/common/district/city",
            "/api/common/district/district"
    );

    @Pointcut("@within(org.springframework.stereotype.Controller) ||" +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public void controllerMethod() {
    }

    @Around("controllerMethod()")
    public Object around(ProceedingJoinPoint jointPoint) throws Throwable {
        if (!ignore() && !SessionUtil.logged()) {
            throw new SystemUserSessionUnavailableException();
        }
        return jointPoint.proceed();
    }

    private boolean ignore() {
        String requestURI = WebUtil.getCurrentRequest().getRequestURI();
        if (requestURI.matches(".*?/dict/.*")) {
            return true;
        }
        return Ignore_Api.contains(requestURI);
    }

}
