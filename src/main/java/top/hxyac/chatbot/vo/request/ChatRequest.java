package top.hxyac.chatbot.vo.request;

public class ChatRequest {

    private String chatSendUuid;

    private String chatReceiveUuid;

    private String chatContent;

    public String getChatSendUuid() {
        return chatSendUuid;
    }

    public void setChatSendUuid(String chatSendUuid) {
        this.chatSendUuid = chatSendUuid;
    }

    public String getChatReceiveUuid() {
        return chatReceiveUuid;
    }

    public void setChatReceiveUuid(String chatReceiveUuid) {
        this.chatReceiveUuid = chatReceiveUuid;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    @Override
    public String toString() {
        return "ChatRequest{" +
                "chatSendUuid='" + chatSendUuid + '\'' +
                ", chatReceiveUuid='" + chatReceiveUuid + '\'' +
                ", chatContent='" + chatContent + '\'' +
                '}';
    }
}
