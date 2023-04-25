package top.hxyac.chatbot.service;

import top.hxyac.chatbot.vo.azure.Gpt35Response;
import top.hxyac.chatbot.vo.request.ChatRequest;

public interface ChatService {
    Integer saveChat(ChatRequest chatRequest, String uuid);

    Gpt35Response getMessage(String wechatId,String uuid,String newMessage);
}
