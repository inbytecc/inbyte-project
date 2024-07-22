package com.inbyte.component.admin.system.user.exception;

/**
 * Session 信息不可用异常
 * 未登录, 或 token 过期, 返回状态码 401, 前端拉起登录窗口
 *
 * @author: chenjw
 * @date: 2020/7/14
 */
public class SystemUserSessionUnavailableException extends RuntimeException {

}
