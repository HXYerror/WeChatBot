package top.hxyac.chatbot.vo.azure;

import java.util.List;

public class Gpt35Response {

    private String id;
    private String object;
    private long created;
    private String model;
    private Gpt35RespUsage usage;
    private List<Gpt35RespChoices> choices;

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setObject(String object) {
        this.object = object;
    }
    public String getObject() {
        return object;
    }

    public void setCreated(long created) {
        this.created = created;
    }
    public long getCreated() {
        return created;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public String getModel() {
        return model;
    }

    public void setUsage(Gpt35RespUsage usage) {
        this.usage = usage;
    }
    public Gpt35RespUsage getUsage() {
        return usage;
    }

    public void setChoices(List<Gpt35RespChoices> choices) {
        this.choices = choices;
    }
    public List<Gpt35RespChoices> getChoices() {
        return choices;
    }

}