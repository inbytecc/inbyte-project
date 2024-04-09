package com.inbyte.component.common.weixin.enterprise.client;

import com.google.common.base.Throwables;
import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 企业微信群机器人消息
 *
 * @author chenjw
 */
@Slf4j
@Component
public class SystemAlarmWeixinEnterpriseClient implements SystemAlarm {

    @Autowired
    private WxCpGroupRobotClient wxCpGroupRobotClient;

    /**
     * 微信消息内容最大长度
     */
    private static final int WeixinMessageMaxLength = 3000;
    /**
     * 故障报警群
     */
    public static final String Weixin_Error_Group_Bot_Key = "a0453b15-3278-4bc6-b3de-8890b760ccf0";

    /**
     * 发送文本消息
     *
     * @return
     */
    public void alert(String module, String requestInfo) {
        alert(module, requestInfo, null);
    }

    /**
     * 发送文本消息
     *
     * @return
     */
    public void alert(String module, String requestInfo, Exception e) {
        StringBuilder content = new StringBuilder()
                .append("应用: ").append(SpringContextUtil.getApplicationName()).append("\n\n")
                .append("模块: ").append(module).append("\n\n")
                .append("环境: ").append(SpringContextUtil.getActiveProfileName()).append("\n\n")
                .append("请求参数:\n").append(requestInfo).append("\n\n")
                .append("异常信息：\n");

        if (e != null) {
            String stackTraceAsString = Throwables.getStackTraceAsString(e);
            if (stackTraceAsString.length() > WeixinMessageMaxLength) {
                content.append(stackTraceAsString, 0, WeixinMessageMaxLength);
            } else {
                content.append(stackTraceAsString);
            }
        }

        content.append("\n报警时间: ").append(LocalDateTime.now()).append("\n\n");

        wxCpGroupRobotClient.sendText(Weixin_Error_Group_Bot_Key, content.toString());
    }


}
