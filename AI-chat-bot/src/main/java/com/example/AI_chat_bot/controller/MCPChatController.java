package com.example.AI_chat_bot.controller;

import com.example.AI_chat_bot.controller.vo.BaseVO;
import com.example.AI_chat_bot.controller.vo.Result;
import com.example.AI_chat_bot.service.MCPChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mcp-chat")
public class MCPChatController {
    @Autowired
    private MCPChatService mcpChatService;
    @PostMapping("/ask")
    public Result<String> ask(String sessionId, String question){
        long startTime = System.currentTimeMillis();
        long endTime;
        Result<String> result = new Result<>();
        try {
            String answer = mcpChatService.ask(sessionId, question);
            result.setCode(200);
            result.setMsg(null);
            result.setData(answer);
            return result;
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            result.setCode(500);
            result.setMsg(e.getMessage());
            return result;
        }
    }

    @DeleteMapping("/delete")
    public Result<Void> clearSession(String session){
        long startTime = System.currentTimeMillis();
        long endTime;
        Result<Void> result = new Result<>();
        try {
            mcpChatService.clearSession(session);
            result.setCode(200);
            result.setMsg(null);
            return result;
        } catch (Exception e){
            result.setCode(500);
            result.setMsg(e.getMessage());
            log.error(e.getMessage());
            return result;
        }
    }
}
