package top.hxyac.chatbot.model;

public enum GPTVersion {

    GPT35("gpt35"),
    GPT40("gpt40");

    private String version;

    GPTVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
