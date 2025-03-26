package com.inbyte.component.common.ai.customer.service.service;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.ai.customer.service.model.AiChatConfig;
import com.inbyte.component.common.ai.customer.service.model.ChatHistoryDTO;
import com.inbyte.component.common.ai.customer.service.model.ChatParam;

import java.util.List;

public interface AiCustomerService {
    
    /**
     * AI对话
     */
    R<String> chatOnWechat(ChatParam param);

    R<String> chatOnMp(String question, Integer userId, String userName, String mctNo);

    R<List<ChatHistoryDTO>> getUserHistory(Integer userId, String mctNo);

    R<AiChatConfig> getConfig(String mctNo);
}