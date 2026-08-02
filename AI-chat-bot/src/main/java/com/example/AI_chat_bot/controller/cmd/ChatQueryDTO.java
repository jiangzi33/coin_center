package com.example.AI_chat_bot.controller.cmd;

public class ChatQueryDTO {
    /** 会话唯一ID，MCP核心标识，区分不同用户对话 */
    private String sessionId;
    /** 用户提问内容 */
    private String question;

    public ChatQueryDTO() {
    }

    public ChatQueryDTO(String sessionId, String question) {
        this.sessionId = sessionId;
        this.question = question;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
