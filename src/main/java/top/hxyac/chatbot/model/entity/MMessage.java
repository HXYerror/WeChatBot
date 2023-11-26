//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package top.hxyac.chatbot.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import top.hxyac.chatbot.vo.azure.Gpt35Message;
import top.hxyac.chatbot.vo.azure.Gpt35RespUsage;

@Document(
        collection = "official_history"
)
public class MMessage {
    @Id
    private String id;
    private Long createTime;
    private Gpt35Message userMessage;
    private Gpt35Message botMessage;
    private String stopReason;
    private Gpt35RespUsage gpt35RespUsage;
    private String requestWechatId;
    private String uuid;

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

    public String getRequestWechatId() {
        return this.requestWechatId;
    }

    public void setRequestWechatId(String requestWechatId) {
        this.requestWechatId = requestWechatId;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return "MMessage{id='" + this.id + '\'' + ", createTime=" + this.createTime + ", userMessage=" + this.userMessage + ", botMessage=" + this.botMessage + ", stopReason='" + this.stopReason + '\'' + ", gpt35RespUsage=" + this.gpt35RespUsage + ", requestWechatId='" + this.requestWechatId + '\'' + ", uuid='" + this.uuid + '\'' + '}';
    }
}
