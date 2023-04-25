package top.hxyac.chatbot.model;

/**
 * @author hxy
 */

public enum EntityEnum {
    Root(2),
    Admin(1),
    User(0),

    Able(0),
    Disable(1),

    Unread(0),
    Read(1);

    /**
     * 返回code
     */
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    EntityEnum(Integer value) {
        this.value = value;
    }
}
