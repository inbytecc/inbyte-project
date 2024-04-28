package com.inbyte.component.app.user.framework.aspect;

import com.alibaba.fastjson2.JSON;
import com.inbyte.component.app.user.framework.SessionUtil;
import com.inbyte.component.app.user.framework.exception.AppUserSessionUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 用户session拦截校验
 *
 * @author chenjw
 **/
@Slf4j
@Aspect
@Component
public class AppSessionCheckAspect {

    /**
     * 登录校验拦截
     */
    @Pointcut("@annotation(com.inbyte.component.app.user.framework.aspect.Logged)")
    public void access() {
    }

    @Around("access()")
    public Object around(ProceedingJoinPoint jointPoint) throws Throwable {
        if (!SessionUtil.logged()) {
            log.info("未注册登录用户拦截:{}", JSON.toJSONString(SessionUtil.getSessionUser()));
            throw new AppUserSessionUnavailableException();
        }
        return jointPoint.proceed();
    }

}
