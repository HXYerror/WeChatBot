package top.hxyac.chatbot.handler;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import top.hxyac.chatbot.service.ChatService;
import top.hxyac.chatbot.vo.azure.Gpt35Response;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class MsgHandler extends BaseLogHandler {

    public  static  final Map<String, Integer> dataMap = new HashMap<>();
    private final ChatService chatService;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        logger.info("getmessage");
        if ("我的信息".equals(wxMessage.getContent())) {
            String text = "user：" + wxMessage.getFromUser() + "\n" +
                    "times：" + dataMap.get(wxMessage.getFromUser());
            WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(text)
                    .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                    .build();
            return m;
        }

        String responseMessage = chatService.getMessage(wxMessage.getFromUser(),wxMessage.getMsgId().toString(),wxMessage.getFromUser(),wxMessage.getContent());

        WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(responseMessage)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return m;

    }
}
