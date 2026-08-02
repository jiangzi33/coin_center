package com.example.AI_chat_bot.service;

import com.example.AI_chat_bot.entity.Message;
import com.example.AI_chat_bot.entity.OpenAIRequest;
import com.example.AI_chat_bot.entity.OpenAIResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OpenAIChatService {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;
    @Value("${spring.ai.openai.api-url}")
    private String apiUrl;
    @Value("${spring.ai.openai.model}")
    private String model;
    @Value("${spring.ai.openai.temperature}")
    private double temperature;

    @Autowired
    private ObjectMapper objectMapper;

    private OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();
    }

    public String chat(List<Message> messages){
        OpenAIRequest openAIRequest = new OpenAIRequest();
        openAIRequest.setMessages(messages);
        openAIRequest.setModel(model);
        String jsonBody = objectMapper.writeValueAsString(openAIRequest);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonBody,mediaType);
        Request request = new Request.Builder()
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " + apiKey)
                .url(apiUrl)
                .post(requestBody)
                .build();

        try{
            Response response = okHttpClient().newCall(request).execute();
            if(!response.isSuccessful()||response.body()==null){
                log.error("调用结果查询失败");
            }
            String string = response.body().string();
            OpenAIResponse openAIResponse = objectMapper.readValue(string, OpenAIResponse.class);
            return openAIResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e){
            log.error("调用结果查询失败");
            return "调用结果查询失败";
        }

    }


}
