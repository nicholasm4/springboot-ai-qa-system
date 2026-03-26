package com.yokior.ai.service;

import com.yokior.ai.domain.ChatMessage;
import com.yokior.ai.domain.dto.ChatRequest;
import com.yokior.ai.domain.dto.ChatResponse;
import com.yokior.ai.domain.vo.ChatSessionVO;
import com.yokior.knowledge.domain.vo.KnowledgeMatchVO;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Yokior
 * @description
 * @date 2025/5/18 23:20
 */
public interface AiChatService
{
    /**
     * 创建新聊天会话
     *
     * @param userId 用户 ID
     * @return 会话 ID
     */
    String createSession(Long userId);

    /**
     * 处理聊天请求
     *
     * @param request 聊天请求
     * @param userId  用户 ID
     * @return 聊天响应
     */
    ChatResponse processChat(ChatRequest request, Long userId);

    /**
     * 获取会话的聊天历史
     *
     * @param sessionId 会话 ID
     * @param userId    用户 ID
     * @return 消息列表
     */
    List<ChatMessage> getChatHistory(String sessionId, Long userId);

    /**
     * 获取会话的最近聊天历史（指定数量）
     *
     * @param sessionId 会话 ID
     * @param userId    用户 ID
     * @param count     消息数量
     * @return 消息列表
     */
    List<ChatMessage> getChatHistory(String sessionId, Long userId, int count);

    /**
     * 保存聊天消息
     *
     * @param message 聊天消息
     */
    void saveChatMessage(ChatMessage message);

    /**
     * 删除会话
     *
     * @param sessionId 会话 ID
     * @param userId    用户 ID
     */
    void deleteSession(String sessionId, Long userId);

    /**
     * 根据用户 id 获取用户会话列表
     *
     * @param userId 用户 ID
     * @return 会话列表
     */
    List<ChatSessionVO> getSessionVoListByUserId(Long userId);

    /**
     * 设置会话标题
     *
     * @param sessionId 会话 ID
     * @param title     标题
     * @return 影响行数
     */
    Boolean setSessionTitle(String sessionId, String title);

    /**
     * 清空会话聊天历史
     *
     * @param sessionId 会话 ID
     * @param userId    用户 ID
     * @return 影响行数
     */
    Boolean clearChatHistory(String sessionId, Long userId);

    /**
     * 处理流式聊天请求
     *
     * @param request  聊天请求
     * @param userId   用户 ID
     * @param response HTTP 响应对象
     * @throws Exception 处理异常
     */
    void processStreamChat(ChatRequest request, Long userId, HttpServletResponse response) throws Exception;

    /**
     * 根据用户选项获取 AI 提供者
     *
     * @param options 用户选项
     * @return AI 提供者名称
     */
    String getAiProviderName(Map<String, Object> options);

    /**
     * 构建知识库增强的 Prompt
     *
     * @param originalPrompt 原始问题
     * @param teamId         团队 ID
     * @return 构建后的 Prompt 和知识匹配结果
     */
    KnowledgePromptResult buildKnowledgePrompt(String originalPrompt, Long teamId);

    /**
     * 检查并执行会话总结（当消息数量超过阈值时）
     *
     * @param sessionId    会话 ID
     * @param userId       用户 ID
     * @param providerName AI 提供者名称
     * @param options      配置选项
     * @return 是否执行了总结
     */
    boolean checkAndSummarizeSession(String sessionId, Long userId, String providerName, Map<String, Object> options);

    /**
     * 对会话历史进行总结
     *
     * @param sessionId    会话 ID
     * @param history      历史消息列表
     * @param providerName AI 提供者名称
     * @param options      配置选项
     * @return 总结后的内容
     */
    String summarizeChatHistory(String sessionId, List<ChatMessage> history, String providerName, Map<String, Object> options);

    /**
     * 知识库 Prompt 构建结果
     */
    class KnowledgePromptResult {
        private String prompt;
        private String questionHash;
        private List<KnowledgeMatchVO> knowledgeMatches;
        private boolean isFrequentQuestion;

        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
        public String getQuestionHash() { return questionHash; }
        public void setQuestionHash(String questionHash) { this.questionHash = questionHash; }
        public List<KnowledgeMatchVO> getKnowledgeMatches() { return knowledgeMatches; }
        public void setKnowledgeMatches(List<KnowledgeMatchVO> knowledgeMatches) { this.knowledgeMatches = knowledgeMatches; }
        public boolean isFrequentQuestion() { return isFrequentQuestion; }
        public void setFrequentQuestion(boolean frequentQuestion) { isFrequentQuestion = frequentQuestion; }
    }
}
