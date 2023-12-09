package top.hxyac.chatbot.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import top.hxyac.chatbot.model.GPTVersion;
import top.hxyac.chatbot.vo.azure.Gpt35Message;
import top.hxyac.chatbot.vo.azure.Gpt35RespUsage;

@Document(
        collection = "flutter_chat_history"
)
public class FlutterMessage {
    @Id
    private String id;
    private Long createTime;
    private Gpt35Message userMessage;
    private Gpt35Message botMessage;
    private String stopReason;
    private Gpt35RespUsage gpt35RespUsage;
    private String userUUID;
    private String chatUUID;
    private Boolean enable;
    private GPTVersion version;
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Gpt35Message getUserMessage() {
        return this.userMessage;
    }

    public void setUserMessage(Gpt35Message userMessage) {
        this.userMessage = userMessage;
    }

    public Gpt35Message getBotMessage() {
        return this.botMessage;
    }

    public void setBotMessage(Gpt35Message botMessage) {
        this.botMessage = botMessage;
    }

    public String getStopReason() {
        return this.stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public Gpt35RespUsage getGpt35RespUsage() {
        return this.gpt35RespUsage;
    }

    public void setGpt35RespUsage(Gpt35RespUsage gpt35RespUsage) {
        this.gpt35RespUsage = gpt35RespUsage;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getChatUUID() {
        return chatUUID;
    }

    public void setChatUUID(String chatUUID) {
        this.chatUUID = chatUUID;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public GPTVersion getVersion() {
        return version;
    }

    public void setVersion(GPTVersion version) {
        this.version = version;
    }
}
