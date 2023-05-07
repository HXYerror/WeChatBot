package top.hxyac.chatbot.service.impl;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import top.hxyac.chatbot.exception.CustomException;
import top.hxyac.chatbot.mapper.ChatMapper;
import top.hxyac.chatbot.model.entity.Chat;
import top.hxyac.chatbot.service.ChatService;
import top.hxyac.chatbot.utils.CommonUtils;
import top.hxyac.chatbot.utils.RedisUtils;
import top.hxyac.chatbot.utils.RequestUtils;
import top.hxyac.chatbot.utils.ResultEnum;
import top.hxyac.chatbot.vo.azure.Gpt35Message;
import top.hxyac.chatbot.vo.azure.Gpt35Request;
import top.hxyac.chatbot.vo.azure.Gpt35Response;
import top.hxyac.chatbot.vo.request.ChatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;

    private final RequestUtils requestUtils;

    private final RedisUtils redisUtils;

    private final static Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

//    @Autowired(required = false)
//    public ChatServiceImpl(ChatMapper chatMapper, RequestUtils requestUtils) {
//        this.chatMapper = chatMapper;
//        this.requestUtils = requestUtils;
//    }


    @Override
    public Integer saveChat(ChatRequest chatRequest, String uuid) {
        Integer result = 0;
        try{
            Chat chat = new Chat();
            chat.setChatContent(chatRequest.getChatContent());
            chat.setChatReceiveUuid(chatRequest.getChatReceiveUuid());
            chat.setChatSendUuid(chatRequest.getChatSendUuid());
            chat.setChatTime(System.currentTimeMillis());
            chat.setChatUuid(CommonUtils.UUID());
            result = chatMapper.insertChatSql(chat);
        }catch (Exception e){
            throw  new CustomException(ResultEnum.MYSQL_ERROR.getCode(),e.getMessage() == null ? e.toString() : e.getMessage(),uuid);
        }
        return result;
    }

    @Override
    public String getMessage(String wechatId,String messageId,String uuid,String newMessage) {

        String responseMessage = getMessageFromRedis(wechatId,messageId,4000L);
        if(responseMessage != null && responseMessage.equals("null")){
            return getMessageFromRedis(wechatId,messageId,1000L);
        }else if(responseMessage != null){
            return responseMessage;
        }

        Gpt35Request gpt35Request = new Gpt35Request();
        Gpt35Message gpt35Message1 = new Gpt35Message("system","You are a helpful assistant.");
        Gpt35Message gpt35Message2 = new Gpt35Message("user",newMessage);
        List<Gpt35Message> gpt35MessageList = new ArrayList<>();
        gpt35MessageList.add(gpt35Message1);
        gpt35MessageList.add(gpt35Message2);
        gpt35Request.setGpt35Message(gpt35MessageList);
        gpt35Request.setFrequency_penalty(0);
        gpt35Request.setMax_tokens(800);
        gpt35Request.setFrequency_penalty(0);
        gpt35Request.setTop_p(0.95);
        gpt35Request.setTemperature(0.7);

        Gpt35Response gpt35Response =  requestUtils.getMessageFromAzureGPT35(gpt35Request,uuid);
        redisUtils.set(redisUtils.prefix+ wechatId + "_" + messageId,gpt35Response.getChoices().get(0).getMessage().getContent(),redisUtils.redisDb);
        redisUtils.expire(redisUtils.prefix+ wechatId + "_" + messageId,30L, redisUtils.redisDb);
        return gpt35Response.getChoices().get(0).getMessage().getContent();
    }

    public String getMessageFromRedis(String wechatId, String messageId, Long time){
        String messageResponse =  redisUtils.get(redisUtils.prefix + wechatId + "_" + messageId, redisUtils.redisDb);
        if(messageResponse == null){
            redisUtils.set(redisUtils.prefix+ wechatId + "_" + messageId,"null", redisUtils.redisDb);
            Long result = redisUtils.expire(redisUtils.prefix+ wechatId + "_" + messageId,30L, redisUtils.redisDb);
            if(result != 1){
                logger.error("set expire error");
            }
            return null;
        }else if("null".equals(messageResponse)){
            try {
                Thread.sleep(2500L);
                return "null";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            logger.info("requestMessage get redis and return");
            return messageResponse;
        }
        return "null";
    }

    @Override
    public WxMpXmlOutMessage getDuplicateMessage(WxMpXmlMessage wxMessage) {
        String responseMessage = getMessageFromRedis(wxMessage.getFromUser(), wxMessage.getMsgId().toString(),4000L);
        if(responseMessage != null && responseMessage.equals("null")){
            responseMessage = getMessageFromRedis(wxMessage.getFromUser(), wxMessage.getMsgId().toString(),1000L);
        }else if(responseMessage == null){
            throw new CustomException(ResultEnum.DUPLICATE_ERROR.getCode(),ResultEnum.DUPLICATE_ERROR.getMsg(),wxMessage.getFromUser());
        }
        WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(responseMessage)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return m;
    }


}
