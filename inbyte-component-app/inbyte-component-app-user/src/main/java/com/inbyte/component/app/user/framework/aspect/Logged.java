package com.inbyte.component.app.user.framework.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * session登录校验注解
 *
 * @author chenjw
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logged {

    /** 已实名认证 */
    boolean authenticated() default false;
}
