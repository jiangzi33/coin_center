package com.example.AI_chat_bot.entity;

import java.util.List;

public class Message {
    private String role;
    private String content;
    private String refusal;
    private List<Object> annotations;

    public Message() {
    }

    public Message(String role, String content, String refusal, List<Object> annotations) {
        this.role = role;
        this.content = content;
        this.refusal = refusal;
        this.annotations = annotations;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRefusal() {
        return refusal;
    }

    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }

    public List<Object> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Object> annotations) {
        this.annotations = annotations;
    }
}
