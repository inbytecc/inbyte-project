package com.inbyte.component.app.user;

import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.component.app.user.framework.exception.AppUserSessionUnavailableException;
import com.inbyte.component.app.user.framework.exception.AppUserUnregisteredException;
import com.inbyte.component.app.user.framework.exception.TelNoBoundException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户状态异常拦截
 *
 * @author chenjw
 * @date 2020/8/7
 **/
@Order(0)
@ControllerAdvice
@Component
public class AppUserSessionExceptionResolver {

    /**
     * 会话失效
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AppUserSessionUnavailableException.class)
    @ResponseBody
    public R appSessionUnavailableException(AppUserSessionUnavailableException e) {
        return R.set(ResultStatus.Unauthorized);
    }

    /**
     * 用户未登录
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AppUserUnregisteredException.class)
    @ResponseBody
    public R appSessionUnregisteredException(AppUserUnregisteredException e) {
        return R.set(ResultStatus.Unregistered);
    }

    /**
     * 未绑定手机号
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = TelNoBoundException.class)
    @ResponseBody
    public R telNoBoundException(TelNoBoundException e) {
        return R.set(ResultStatus.Unregistered);
    }

}