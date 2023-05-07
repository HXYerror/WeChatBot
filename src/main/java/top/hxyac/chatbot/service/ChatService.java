package top.hxyac.chatbot.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import top.hxyac.chatbot.vo.azure.Gpt35Response;
import top.hxyac.chatbot.vo.request.ChatRequest;

public interface ChatService {
    Integer saveChat(ChatRequest chatRequest, String uuid);

    String getMessage(String wechatId,String messageId,String uuid,String newMessage);

    WxMpXmlOutMessage getDuplicateMessage(WxMpXmlMessage wxMessage);

}

