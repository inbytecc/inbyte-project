package com.inbyte.component.common.ai.customer.service.service.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.Assert;
import com.inbyte.commons.util.MD5Util;
import com.inbyte.component.common.ai.customer.service.dao.AiChatHistoryMapper;
import com.inbyte.component.common.ai.customer.service.dao.AiQuestionCountMapper;
import com.inbyte.component.common.ai.customer.service.dao.AiRobotConfigMapper;
import com.inbyte.component.common.ai.customer.service.dao.AiScriptLibraryMapper;
import com.inbyte.component.common.ai.customer.service.model.AiChatConfig;
import com.inbyte.component.common.ai.customer.service.model.ChatHistoryDTO;
import com.inbyte.component.common.ai.customer.service.model.ChatParam;
import com.inbyte.component.common.ai.customer.service.model.po.AiChatHistoryPo;
import com.inbyte.component.common.ai.customer.service.model.po.AiQuestionCountPo;
import com.inbyte.component.common.ai.customer.service.model.po.AiRobotConfigPo;
import com.inbyte.component.common.ai.customer.service.model.po.AiScriptLibraryPo;
import com.inbyte.component.common.ai.customer.service.service.AiCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AiScriptLibraryMapper aiScriptLibraryMapper;
    @Autowired
    private AiRobotConfigMapper aiRobotConfigMapper;

    @Override
    public R<String> chatOnWechat(ChatParam chatParam) {
        // 计算问题的hash值用于相似问题判断
        String answer = getAnswer(chatParam.getQuestion(), chatParam.getMctNo());

        String questionHash = MD5Util.md5(chatParam.getQuestion());
        // 保存对话记录
        AiChatHistoryPo chatHistory = new AiChatHistoryPo();
        chatHistory.setMctNo(chatParam.getMctNo());
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
        questionCount.setMctNo(chatParam.getMctNo());
        aiQuestionCountMapper.insertOrUpdate(questionCount);

        return R.okStr(answer);
    }

    private String getAnswer(String question, String mctNo) {
        // 先从话术库中查找匹配的问题（模糊查询）
        R<String> r = getLocalScriptLibrary(question, mctNo);
        if (r.succeeded()) {
            return r.getData();
        } else {
            // 如果在话术库中没有找到匹配的问题或关键词，则调用AI接口
            AiRobotConfigPo aiRobotConfigPo = aiRobotConfigMapper.selectOne(new LambdaQueryWrapper<AiRobotConfigPo>()
                    .eq(AiRobotConfigPo::getMctNo, mctNo));
            Assert.notNull(aiRobotConfigPo, "该商户暂未支持AI客服哦");

            // 调用AI接口
            ApplicationParam applicationParam = ApplicationParam.builder()
                    .apiKey(aiRobotConfigPo.getApiKey())
                    .appId(aiRobotConfigPo.getAppId())
                    .prompt(question)
                    .build();

            Application application = new Application();
            ApplicationResult result;
            try {
                result = application.call(applicationParam);
            } catch (NoApiKeyException e) {
                throw InbyteException.failure("暂未支持哦");
            } catch (InputRequiredException e) {
                throw InbyteException.error("客服功能异常");
            }
            return result.getOutput().getText();
        }
    }
    @Override
    public R<String> chatOnMp(String question, Integer userId, String userName, String mctNo) {
        // 计算问题的hash值用于相似问题判断
        String questionHash = MD5Util.md5(question);

        // 调用AI接口
        String answer = getAnswer(question, mctNo);

        // 保存对话记录
        AiChatHistoryPo chatHistory = new AiChatHistoryPo();
        chatHistory.setMctNo(mctNo);
        chatHistory.setUserId(userId);
        chatHistory.setSender(userName);
        chatHistory.setReceiver("ROBOT");
        chatHistory.setClient("WXMP");
        chatHistory.setQuestion(question);
        chatHistory.setQuestionHash(questionHash);
        chatHistory.setAnswer(answer);
        chatHistory.setCreateTime(LocalDateTime.now());
        aiChatHistoryMapper.insert(chatHistory);

        // 创建问题统计记录
        AiQuestionCountPo questionCount = new AiQuestionCountPo();
        questionCount.setQuestion(question);
        questionCount.setQuestionHash(questionHash);
        questionCount.setMctNo(mctNo);
        aiQuestionCountMapper.insertOrUpdate(questionCount);

        return R.okStr(answer);
    }


    public R<String> getLocalScriptLibrary(String question, String mctNo) {
        AiScriptLibraryPo aiScriptLibraryPo = aiScriptLibraryMapper.findByKeyword(question, mctNo);
        // 先从话术库中查找匹配的问题（模糊查询）
        if (aiScriptLibraryPo == null) {
            aiScriptLibraryPo = aiScriptLibraryMapper.findByQuestion(question, mctNo);
        }

        if (aiScriptLibraryPo != null) {
            // 找到匹配的问题话术，增加命中次数
            aiScriptLibraryMapper.incrementHitCount(aiScriptLibraryPo.getScriptId());
            return R.okStr(aiScriptLibraryPo.getAnswer());
        }

        return R.failure("没有找到匹配的问题");
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
        if ("easyweb".equals(mctNo) ||
                "junyou".equals(mctNo) ||
                "jingsheng".equals(mctNo)) {
            enabled = 1;
        }
        AiChatConfig aiChatConfig = new AiChatConfig();
        aiChatConfig.setEnabled(enabled);
        return R.ok(aiChatConfig);
    }
}