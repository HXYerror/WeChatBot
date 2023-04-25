package top.hxyac.chatbot.exception;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException{

    private Integer code;

    private String msg;

    private String uuid;

    public CustomException(Integer code, String msg,String uuid) {
        this.code = code;
        this.msg = msg;
        this.uuid = uuid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
