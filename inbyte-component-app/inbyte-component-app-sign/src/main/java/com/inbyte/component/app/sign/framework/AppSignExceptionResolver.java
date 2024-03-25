package com.inbyte.component.app.sign.framework;

import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.component.app.sign.framework.exception.AppTokenUnavailableException;
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
public class AppSignExceptionResolver {

     /**
     * AppToken 失效
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AppTokenUnavailableException.class)
    @ResponseBody
    public R appTokenUnavailableException(AppTokenUnavailableException e) {
        return R.set(ResultStatus.App_Token_Unavailable);
    }
}