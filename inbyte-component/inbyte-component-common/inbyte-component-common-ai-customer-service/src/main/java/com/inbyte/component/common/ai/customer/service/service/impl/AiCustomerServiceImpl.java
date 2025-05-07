package com.inbyte.component.common.ai.customer.service.service.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.Assert;
import com.inbyte.commons.util.MD5Util;
import com.inbyte.commons.util.StringUtil;
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
    private AiScriptLibraryMapper aiScriptLibraryMapper;
    @Autowired
    private AiRobotConfigMapper aiRobotConfigMapper;

    @Override
    public R<String> chatOnWechat(ChatParam chatParam) {
        String sessionId = chatParam.getSender() + LocalDate.now().toString().substring(0, 7);
        // 计算问题的hash值用于相似问题判断
        String answer = getAnswer(chatParam.getQuestion(), chatParam.getMctNo(), sessionId);

        String questionHash = MD5Util.md5(chatParam.getQuestion());
        // 保存对话记录
        AiChatHistoryPo chatHistory = new AiChatHistoryPo();
        chatHistory.setSessionId(sessionId);
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

    private String getAnswer(String question, String mctNo, String sessionId) {
        // 先从话术库中查找匹配的问题（模糊查询）
        R<String> r = getLocalScriptLibrary(question, mctNo);
        if (r.succeeded()) {
            return r.getData();
        } else {
            // 如果在话术库中没有找到匹配的问题或关键词，则调用AI接口
            AiRobotConfigPo aiRobotConfigPo = aiRobotConfigMapper.selectOne(new LambdaQueryWrapper<AiRobotConfigPo>()
                    .eq(AiRobotConfigPo::getMctNo, mctNo));
            Assert.notNull(aiRobotConfigPo, "该商户暂未支持AI客服哦");

            List<Message> messages = getMessages(question, sessionId, mctNo);
            // 调用AI接口
            ApplicationParam applicationParam = ApplicationParam.builder()
                    .apiKey(aiRobotConfigPo.getApiKey())
                    .appId(aiRobotConfigPo.getAppId())
                    .messages(messages)
//                    .sessionId(sessionId)
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
        String questionHash = MD5Util.md5(question);

        // 调用AI接口
        String answer = getAnswer(question, mctNo, userId.toString());

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
        if (StringUtil.isEmpty(question)) {
            return R.failure("问题不能为空");
        }
        if (question.length() > 10) {
            return R.failure("关键字无法匹配");
        }
        AiScriptLibraryPo aiScriptLibraryPo = aiScriptLibraryMapper.findByKeyword(question, mctNo);
        // 先从话术库中查找匹配的问题（模糊查询）
        if (aiScriptLibraryPo == null) {
            aiScriptLibraryPo = aiScriptLibraryMapper.findByQuestion(question, mctNo);
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
        AiChatConfig aiChatConfig = new AiChatConfig();
        aiChatConfig.setEnabled(enabled);
        return R.ok(aiChatConfig);
    }

    private List<Message> getMessages(String question, String sessionId, String mctNo) {
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("# 角色\n" +
                        "你是白鹭谷专业客服小白，你的回答需要专业、严谨、简洁、友好、说人话。\n" +
                        "## 技能\n" +
                        "### 技能 1：直接返回结果\n" +
                        "- **任务**：根据用户输入的提示词以及知识库中的内容，直接给出答案，无需进行推理。\n" +
                        "- **要求1**：\n" +
                        "  - 回答问题时，确保内容简短且准确。\n" +
                        "  - 如果不在知识库里的内容，稍等我同事来了给您确定。\n" +
                        "  - 可以偶尔适当使用连续句话感叹号微信表情或Emoji等，显得像真人一样。\n" +
                        "- **要求2**：\n" +
                        "  - 回复信息拟人化方式，信息简短，可以分成多条回复" +
                        "- **要求3**：" +
                        "  - 如果问题很抽象或者很简单无法判断意图，那么就像真人一样很简单3~5个字交流引导需要什么帮助" +
                        "## 限制\n" +
                        "- 只回答与知识库内容相关的问题。\n" +
                        "- 不要在知识库外进行推理或提供未经验证的信息。\n" +
                        "- 回答内容必须简洁明了，避免冗长和复杂的解释。\n" +
                        "# 格式" +
                        "- 强制使用JSON数组格式返回" +
                        "- 返回字段包括msg, msgType\n" +
                        "- msgType类型包括text, image, video, audio, link, location, event\n" +
                        "\n" +
                        "# 知识库\n" +
                        "请记住以下材料，他们可能对回答问题有帮助。\n" +
                        "${documents}")
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