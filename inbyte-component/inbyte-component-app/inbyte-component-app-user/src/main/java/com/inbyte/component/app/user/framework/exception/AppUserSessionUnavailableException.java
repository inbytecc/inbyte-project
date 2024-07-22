package com.inbyte.component.app.user.framework.exception;

/**
 * Session 信息不可用异常
 * 未登录, 或 token 过期, 返回状态码 401, 前端拉起登录窗口
 *
 * @author: chenjw
 * @date: 2020/7/14
 */
public class AppUserSessionUnavailableException extends RuntimeException {

}
