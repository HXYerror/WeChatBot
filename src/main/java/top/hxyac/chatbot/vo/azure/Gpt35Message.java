package top.hxyac.chatbot.vo.azure;

public class Gpt35Message {

    private String role;
    private String content;

    public Gpt35Message() {
    }

    public Gpt35Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Gpt35Message{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
