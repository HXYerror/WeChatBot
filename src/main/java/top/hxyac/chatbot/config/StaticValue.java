package top.hxyac.chatbot.config;

public class StaticValue {
    public static final String WECHAT_AUTH = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={jsCode}&grant_type=authorization_code";

    public static final String WECHAT_TOKEN = "GET https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";

    public static  final String APPID = "";

    public static final String SECRET = "";
}
