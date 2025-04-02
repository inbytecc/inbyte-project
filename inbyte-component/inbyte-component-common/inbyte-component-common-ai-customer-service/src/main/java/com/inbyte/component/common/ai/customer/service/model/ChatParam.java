package com.inbyte.component.common.ai.customer.service.model;

import lombok.*;

/**
 * 聊天请求参数
 *
 * @author chenjw
 * @date 2024-05-21 17:14:36
 **/
@Getter
@Setter
public class ChatParam {

    /** 问题 */
    private String question;
    /** 发送者 */
    private String sender;
    /** 接受者 */
    private String receiver;
    /** apikey */
    private String apiKey;
    /** 商户号 */
    private String mctNo;
    /** 门店ID */
    private String venueId;

}
