package top.hxyac.chatbot.service.impl;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.hxyac.chatbot.exception.CustomException;
import top.hxyac.chatbot.mapper.ChatMapper;
import top.hxyac.chatbot.model.entity.Chat;
import top.hxyac.chatbot.model.entity.MMessage;
import top.hxyac.chatbot.service.ChatService;
import top.hxyac.chatbot.utils.CommonUtils;
import top.hxyac.chatbot.utils.RedisUtils;
import top.hxyac.chatbot.utils.RequestUtils;
import top.hxyac.chatbot.utils.ResultEnum;
import top.hxyac.chatbot.vo.azure.Gpt35Message;
import top.hxyac.chatbot.vo.azure.Gpt35Request;
import top.hxyac.chatbot.vo.azure.Gpt35RespChoices;
import top.hxyac.chatbot.vo.azure.Gpt35Response;
import top.hxyac.chatbot.vo.request.ChatRequest;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;

    private final RequestUtils requestUtils;

    private final RedisUtils redisUtils;

    private final MongoTemplate mongoTemplate;
    private final String collectionName = "official_history";
    @Value("${hxyac.history.num}")
    private int historyNum;
    @Value("${hxyac.toekn.max}")
    private int tokenMax;

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
    public String getMessage(String wechatId, String messageId, String uuid, String newMessage) {
        long startTime = System.currentTimeMillis();
        String responseMessage = this.getMessageFromRedis(wechatId, messageId, 4000L);
        if (responseMessage != null && responseMessage.equals("null")) {
            return this.getMessageFromRedis(wechatId, messageId, 1000L);
        } else if (responseMessage != null) {
            return responseMessage;
        } else {
            Gpt35Request gpt35Request = new Gpt35Request();
            List<Gpt35Message> gpt35MessageList = new ArrayList();
            Gpt35Message gpt35Message1 = new Gpt35Message("system", "Your name is lihua(李华). You need to chat with me and answer my all questions. And you need to speak chinese if user not speak other language.Pay attention to the other person's emotions and show care.");
            gpt35MessageList.add(gpt35Message1);
            ArrayList<MMessage> historyMessageList = (ArrayList)this.findLatest20Records(uuid);
            Collections.reverse(historyMessageList);
            historyMessageList.forEach((item) -> {
                gpt35MessageList.add(item.getUserMessage());
                gpt35MessageList.add(item.getBotMessage());
            });
            Gpt35Message gpt35Message2 = new Gpt35Message("user", newMessage);
            gpt35MessageList.add(gpt35Message2);
            gpt35Request.setGpt35Message(gpt35MessageList);
            gpt35Request.setFrequency_penalty(0);
            gpt35Request.setMax_tokens(this.tokenMax);
            gpt35Request.setFrequency_penalty(0);
            gpt35Request.setTop_p(0.8);
            gpt35Request.setTemperature(0.5);
            Gpt35Response gpt35Response = this.requestUtils.getMessageFromAzureGPT35(gpt35Request, uuid);
            this.redisUtils.set(this.redisUtils.prefix + wechatId + "_" + messageId, ((Gpt35RespChoices)gpt35Response.getChoices().get(0)).getMessage().getContent(), this.redisUtils.redisDb);
            this.redisUtils.expire(this.redisUtils.prefix + wechatId + "_" + messageId, 30L, this.redisUtils.redisDb);
            MMessage mMessage = new MMessage();
            mMessage.setBotMessage(((Gpt35RespChoices)gpt35Response.getChoices().get(0)).getMessage());
            mMessage.setUserMessage(gpt35Message2);
            mMessage.setCreateTime(gpt35Response.getCreated());
            mMessage.setGpt35RespUsage(gpt35Response.getUsage());
            mMessage.setRequestWechatId(wechatId);
            mMessage.setStopReason(((Gpt35RespChoices)gpt35Response.getChoices().get(0)).getFinish_reason());
            mMessage.setUuid(uuid);
            this.mongoTemplate.save(mMessage, "official_history");
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            logger.warn("messageId:" + messageId + "\n runTime:" + executionTime);
            return ((Gpt35RespChoices)gpt35Response.getChoices().get(0)).getMessage().getContent();
        }
    }

    public String getMessageFromRedis(String wechatId, String messageId, Long time) {
        String messageResponse = this.redisUtils.get(this.redisUtils.prefix + wechatId + "_" + messageId, this.redisUtils.redisDb);
        if (messageResponse == null) {
            this.redisUtils.set(this.redisUtils.prefix + wechatId + "_" + messageId, "null", this.redisUtils.redisDb);
            Long result = this.redisUtils.expire(this.redisUtils.prefix + wechatId + "_" + messageId, 30L, this.redisUtils.redisDb);
            if (result != 1L) {
                logger.error("set expire error");
            }

            return null;
        } else if ("null".equals(messageResponse)) {
            try {
                Thread.sleep(2500L);
                return "null";
            } catch (InterruptedException var6) {
                var6.printStackTrace();
                return "null";
            }
        } else {
            logger.info("requestMessage get redis and return");
            return messageResponse;
        }
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

    public List<MMessage> testGetMessage(String uuid) {
        return this.findLatest20Records(uuid);
    }

    public List<MMessage> findLatest20Records(String uuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(uuid));
        query.with(Sort.by(Direction.DESC, new String[]{"createTime"}));
        query.limit(this.historyNum);
        return this.mongoTemplate.find(query, MMessage.class, "official_history");
    }
}
