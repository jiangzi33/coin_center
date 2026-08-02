package com.example.AI_chat_bot.context;

import com.example.AI_chat_bot.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class McpContextManager {
    @Value("${spring.ai.openai.max-message-size}")
    private int maxMessageSize;
    private final Map<String, List<Message>> sessionContextMap = new ConcurrentHashMap<>();

    public List<Message> getContext(String session){
        List<Message> messages = sessionContextMap.get(session);
        if(messages==null){
            messages = new ArrayList<>();
            Message message = new Message();
            message.setRole("system");
            message.setContent("你是一个专业的问答机器人，回答问题要求简洁清晰，要求严谨");
            messages.add(message);
            sessionContextMap.put(session,messages);
        }
        return messages;
    }

    public void appendMessage(String session, Message message){
        List<Message> messages = getContext(session);
        messages.add(message);
        //清除上下文的时候不能删掉角色定义，要从第一条实际消息开始删除
        if(messages.size()>maxMessageSize){
            messages.remove(1);
        }
    }

    public void clearSession(String session){
        sessionContextMap.remove(session);
    }
}
