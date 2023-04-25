package top.hxyac.chatbot.vo.wechat;

/**
 * @author hxy
 */
public class MsgCheckReq {

    private String openid;

    private Integer scene;

    private Integer version;

    private String content;

    public MsgCheckReq(String openid, Integer scene, Integer version, String content) {
        this.openid = openid;
        this.scene = scene;
        this.version = version;
        this.content = content;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MsgCheckReq{" +
                "openid='" + openid + '\'' +
                ", scene=" + scene +
                ", version=" + version +
                ", content='" + content + '\'' +
                '}';
    }

}
