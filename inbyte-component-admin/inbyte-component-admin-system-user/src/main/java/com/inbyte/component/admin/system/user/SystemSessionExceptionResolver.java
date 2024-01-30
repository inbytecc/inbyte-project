package com.inbyte.component.admin.system.user;

import com.inbyte.component.admin.system.user.exception.SystemUserSessionUnavailableException;
import com.inbyte.component.admin.system.user.exception.TelNoBoundException;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.component.admin.system.user.exception.AdminUnavailableException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常拦截
 *
 * @author chenjw
 * @date 2020/8/7
 **/
@Order(0)
@ControllerAdvice
@Component
public class SystemSessionExceptionResolver {

    /**
     * 用户未登录
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = SystemUserSessionUnavailableException.class)
    @ResponseBody
    public R systemUserSessionUnavailableException(SystemUserSessionUnavailableException e) {
        return R.set(ResultStatus.Unauthorized);
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

    /**
     * 非超管操作异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AdminUnavailableException.class)
    @ResponseBody
    public R adminUnavailableException(AdminUnavailableException e) {
        return R.failure("只有超管才允许操作此功能");
    }
}