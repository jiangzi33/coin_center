package com.example.AI_chat_bot.entity;

import java.util.List;

public class OpenAIResponse {
    private String id;
    private String object;
    private Integer created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String service_tier;
    private String system_fingerprint;

    public OpenAIResponse() {
    }

    public OpenAIResponse(String id, String object, Integer created, String model, List<Choice> choices, Usage usage, String service_tier, String system_fingerprint) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.usage = usage;
        this.service_tier = service_tier;
        this.system_fingerprint = system_fingerprint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public String getService_tier() {
        return service_tier;
    }

    public void setService_tier(String service_tier) {
        this.service_tier = service_tier;
    }

    public String getSystem_fingerprint() {
        return system_fingerprint;
    }

    public void setSystem_fingerprint(String system_fingerprint) {
        this.system_fingerprint = system_fingerprint;
    }
}
