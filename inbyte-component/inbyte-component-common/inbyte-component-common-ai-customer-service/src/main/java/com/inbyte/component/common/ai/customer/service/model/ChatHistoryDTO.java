package com.inbyte.component.common.ai.customer.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryDTO {
    /**
     * 消息类型：user/ai
     */
    private String type;
    
    /**
     * 消息内容
     */
    private String content;
    
}