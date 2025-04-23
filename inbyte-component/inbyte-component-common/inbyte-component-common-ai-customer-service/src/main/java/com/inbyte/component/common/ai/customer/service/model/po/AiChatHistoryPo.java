package com.inbyte.component.common.ai.customer.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * AI聊天历史记录
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ai_chat_history")
public class AiChatHistoryPo {
    
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户号
     */
    private String mctNo;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 问题内容
     */
    private String question;

    /**
     * AI回答内容
     */
    private String answer;

    /**
     * 消息发送者
     */
    private String sender;

    /**
     * 客服接受者
     */
    private String receiver;

    /**
     * 客户端类型
     * 小程序 微信
     */
    private String client;

    /**
     * 问题hash值(用于相似问题判断)
     */
    private String questionHash;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}