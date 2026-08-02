package com.example.AI_chat_bot.service;

import com.example.AI_chat_bot.context.McpContextManager;
import com.example.AI_chat_bot.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MCPChatService {
    @Autowired
    private OpenAIChatService openAIChatService;
    @Autowired
    private McpContextManager mcpContextManager;

    public String ask(String sessionId, String question){
        Message message = new Message();
        message.setRole("user");
        message.setContent(question);
        List<Message> messages = mcpContextManager.getContext(sessionId);
        messages.add(message);
        mcpContextManager.appendMessage(sessionId,message);
        String answer = openAIChatService.chat(messages);
        Message message1 = new Message();
        message1.setRole("assistant");
        message1.setContent(answer);
        mcpContextManager.appendMessage(sessionId,message1);
        return answer;
    }

    public void clearSession(String sessionId){
        mcpContextManager.clearSession(sessionId);
    }
}
