package com.inbyte.component.common.ai.customer.service.service.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.app.RagOptions;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.JsonObject;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.Assert;
import com.inbyte.commons.util.MD5Util;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.common.ai.customer.service.dao.*;
import com.inbyte.component.common.ai.customer.service.model.AiChatConfig;
import com.inbyte.component.common.ai.customer.service.model.ChatHistoryDTO;
import com.inbyte.component.common.ai.customer.service.model.ChatParam;
import com.inbyte.component.common.ai.customer.service.model.po.*;
import com.inbyte.component.common.ai.customer.service.service.AiCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AiCustomerServiceImpl implements AiCustomerService {

    @Autowired
    private AiChatHistoryMapper aiChatHistoryMapper;
    @Autowired
    private AiQuestionCountMapper aiQuestionCountMapper;
    @Autowired
    private AiKnowledgeMapper aiKnowledgeMapper;
    @Autowired
    private AiRobotConfigMapper aiRobotConfigMapper;
    @Autowired
    private AiRobotCustomerServiceMapper aiRobotCustomerServiceMapper;

    @Override
    public R<String> chatOnWechat(ChatParam chatParam) {
        // 如果在知识库中没有找到匹配的问题或关键词，则调用AI接口
        AiRobotCustomerServicePo aiRobotCustomerServicePo = aiRobotCustomerServiceMapper.selectOne(new LambdaQueryWrapper<AiRobotCustomerServicePo>()
                .eq(AiRobotCustomerServicePo::getApiKey, chatParam.getApiKey()));
        Assert.notNull(aiRobotCustomerServicePo, "该客服 API KEY 无效哦");

        String sessionId = chatParam.getSender() + LocalDate.now().toString().substring(0, 7);
        // 计算问题的hash值用于相似问题判断
        String answer = getAnswer(chatParam.getQuestion(), aiRobotCustomerServicePo, sessionId);

        String questionHash = MD5Util.md5(chatParam.getQuestion());
        // 保存对话记录
        AiChatHistoryPo chatHistory = new AiChatHistoryPo();
        chatHistory.setSessionId(sessionId);
        chatHistory.setMctNo(aiRobotCustomerServicePo.getMctNo());
        chatHistory.setQuestion(chatParam.getQuestion());
        chatHistory.setQuestionHash(questionHash);
        chatHistory.setAnswer(answer);
        chatHistory.setSender(chatParam.getSender());
        chatHistory.setReceiver(chatParam.getReceiver());
        chatHistory.setClient("WECHAT");
        chatHistory.setCreateTime(LocalDateTime.now());
        aiChatHistoryMapper.insert(chatHistory);

        // 创建问题统计记录
        AiQuestionCountPo questionCount = new AiQuestionCountPo();
        questionCount.setQuestion(chatParam.getQuestion());
        questionCount.setQuestionHash(questionHash);
        questionCount.setMctNo(aiRobotCustomerServicePo.getMctNo());
        aiQuestionCountMapper.insertOrUpdate(questionCount);

        return R.okStr(answer);
    }

    private String getAnswer(String question, AiRobotCustomerServicePo aiRobotCustomerServicePo, String sessionId) {
        // 先从知识库中查找匹配的问题（模糊查询）
        R<String> r = getLocalScriptLibrary(question, aiRobotCustomerServicePo.getMctNo());
        if (r.succeeded()) {
            return r.getData();
        } else {
            // 如果在知识库中没有找到匹配的问题或关键词，则调用AI接口
            AiRobotConfigPo aiRobotConfigPo = aiRobotConfigMapper.selectOne(new LambdaQueryWrapper<AiRobotConfigPo>()
                    .eq(AiRobotConfigPo::getMctNo, aiRobotCustomerServicePo.getMctNo())
                    .eq(AiRobotConfigPo::getVenueId, aiRobotCustomerServicePo.getVenueId()));
            Assert.notNull(aiRobotConfigPo, "该商户暂未支持AI客服哦");

//            List<Message> messages = getMessages(aiRobotConfigPo, question, sessionId, aiRobotConfigPo.getMctNo());

            JsonObject metadataFilter = new JsonObject();
            metadataFilter.addProperty("mct_no", aiRobotConfigPo.getMctNo());
            metadataFilter.addProperty("venue_id", aiRobotConfigPo.getVenueId());

//            JsonObject structureFilter = new JsonObject();
//            structureFilter.addProperty("key", "structured123");

            RagOptions ragOptions = RagOptions.builder()
//                    .pipelineIds(Collections.singletonList("wwht5d7ebf"))
                    .metadataFilter(metadataFilter)
//                    .tags(Collections.singletonList("tag_123"))
//                    .fileIds(Collections.singletonList("files_123"))
//                    .structuredFilter(structureFilter)
                    .build();

            // 调用AI接口
            ApplicationParam applicationParam = ApplicationParam.builder()
                    .apiKey(aiRobotConfigPo.getApiKey())
                    .appId(aiRobotConfigPo.getAppId())
                    .prompt(question)
                    .sessionId(sessionId)
//                    .ragOptions(ragOptions)
                    .build();

            log.info("调用AI接口参数：{}", applicationParam.toString());
            Application application = new Application();
            ApplicationResult result;
            try {
                result = application.call(applicationParam);
            } catch (NoApiKeyException e) {
                throw InbyteException.fail("暂未支持哦");
            } catch (InputRequiredException e) {
                throw InbyteException.error("客服功能异常");
            }
            log.info("AI返回结果：{}", JSON.toJSONString(result));
            String answer = result.getOutput().getText();
            if (answer.contains("```json")) {
                answer = answer.substring(answer.indexOf("```json\n") + 8, answer.lastIndexOf("\n```"));
            }
            return answer;
        }
    }

    @Override
    public R<String> chatOnMp(String question, Integer userId, String userName, String mctNo) {
        // 计算问题的hash值用于相似问题判断
//        String questionHash = MD5Util.md5(question);

        // 调用AI接口
//        String answer = getAnswer(question, mctNo, userId.toString());
//
//        // 保存对话记录
//        AiChatHistoryPo chatHistory = new AiChatHistoryPo();
//        chatHistory.setMctNo(mctNo);
//        chatHistory.setUserId(userId);
//        chatHistory.setSender(userName);
//        chatHistory.setReceiver("ROBOT");
//        chatHistory.setClient("WXMP");
//        chatHistory.setQuestion(question);
//        chatHistory.setQuestionHash(questionHash);
//        chatHistory.setAnswer(answer);
//        chatHistory.setCreateTime(LocalDateTime.now());
//        aiChatHistoryMapper.insert(chatHistory);
//
//        // 创建问题统计记录
//        AiQuestionCountPo questionCount = new AiQuestionCountPo();
//        questionCount.setQuestion(question);
//        questionCount.setQuestionHash(questionHash);
//        questionCount.setMctNo(mctNo);
//        aiQuestionCountMapper.insertOrUpdate(questionCount);

        return R.ok();
    }


    public R<String> getLocalScriptLibrary(String question, String mctNo) {
        if (StringUtil.isEmpty(question)) {
            return R.fail("问题不能为空");
        }
        AiKnowledgePo aiKnowledgePo = aiKnowledgeMapper.findByQuestion(question, question.replaceAll("[\n|\t]", ""), mctNo);
        // 先从知识库中查找匹配的问题（模糊查询）
        if (aiKnowledgePo != null) {
            return R.okStr(aiKnowledgePo.getAnswer());
        }

        return R.fail("没有找到匹配的问题");
    }


    @Override
    public R<List<ChatHistoryDTO>> getUserHistory(Integer userId, String mctNo) {
        List<AiChatHistoryPo> history = aiChatHistoryMapper.selectList(new LambdaQueryWrapper<AiChatHistoryPo>()
                .eq(AiChatHistoryPo::getUserId, userId)
                .eq(AiChatHistoryPo::getMctNo, mctNo));

        List<ChatHistoryDTO> chatHistory = new ArrayList<>();
        for (AiChatHistoryPo chat : history) {
            if (chat.getQuestion() != null) {
                chatHistory.add(new ChatHistoryDTO("user", chat.getQuestion()));
            }
            if (chat.getAnswer() != null) {
                chatHistory.add(new ChatHistoryDTO("ai", chat.getAnswer()));
            }
        }
        return R.ok(chatHistory);
    }

    @Override
    public R<AiChatConfig> getConfig(String mctNo) {
        Integer enabled = 0;
        AiChatConfig aiChatConfig = new AiChatConfig();
        aiChatConfig.setEnabled(enabled);
        return R.ok(aiChatConfig);
    }

    private List<Message> getMessages(AiRobotConfigPo aiRobotConfigPo, String question, String sessionId, String mctNo) {
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(aiRobotConfigPo.getCustomerServiceSystemPrompt())
                .build();
        List<Message> messages = new ArrayList<>();
        messages.add(systemMsg);

        List<AiChatHistoryPo> history = aiChatHistoryMapper.selectList(new LambdaQueryWrapper<AiChatHistoryPo>()
                .eq(AiChatHistoryPo::getSessionId, sessionId)
                .eq(AiChatHistoryPo::getMctNo, mctNo)
                .orderByDesc(AiChatHistoryPo::getId)
                .last("limit 5"));
        for (AiChatHistoryPo chat : history) {
            messages.add(Message.builder()
                    .role(Role.USER.getValue())
                    .content(chat.getQuestion())
                    .build());

            if (JSON.isValid(chat.getAnswer())) {
                JSONArray jsonArray = JSON.parseArray(chat.getAnswer());
                for (Object obj : jsonArray) {
                    JSONObject jsonObject = (JSONObject) obj;
                    String msgType = jsonObject.getString("msgType");
                    if ("text".equals(msgType)) {
                        messages.add(Message.builder()
                                .role(Role.ASSISTANT.getValue())
                                .content(jsonObject.getString("msg"))
                                .build());
                    }
                }
            } else {
                messages.add(Message.builder()
                        .role(Role.ASSISTANT.getValue())
                        .content(chat.getAnswer())
                        .build());
            }
        }

        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(question)
                .build();
        messages.add(userMsg);
        return messages;
    }
}