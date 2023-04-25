package top.hxyac.chatbot.utils;

/**
 * @author hxy
 */

public enum ResultEnum {

    SUCCESS(0, "success"),
    UNKNOWN_ERROR(1000, "未知错误"),
    MYSQL_ERROR(1001,"数据库错误"),
    USER_NOT_LOGIN(2000, "该用户未登录"),
    USER_NOT_INFO(2001, "该用户未授权"),
    REPEAT_REGISTER(2002, "该用户已注册"),
    USER_NOT_EXIST(2003, "不存在该用户"),
    USER_BAN(2004, "用户被封禁"),
    USER_NOT_ADMIN(2004, "用户无权限"),
    USER_TOKEN_ERROR(2005, "用户token错误"),
    USER_NOT_ROOT(2006, "用户无权限"),
    MESSAGE_CHECK_RISKY(3000,"内容非法，请重试"),
    MESSAGE_CHECK_FAIL(3001,"操作失败，请重试"),
    LIKE_DISLIKE_LIMIT(5001,"重复操作");

    /**
     * 返回code
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
