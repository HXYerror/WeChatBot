package top.hxyac.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import top.hxyac.chatbot.config.EnvEnum;
import top.hxyac.chatbot.model.GPTVersion;
import top.hxyac.chatbot.vo.request.promptMessage;
import top.hxyac.chatbot.service.ChatService;
import top.hxyac.chatbot.utils.JsonData;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
/**
 * @author hxy
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/pri/chat")
@Api(value = "chat Rest Controller")
public class ChatController {

    private final ChatService chatService;
    @Value("${spring.profiles.active}")
    private String env;

    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);

    @PostMapping(value="gptmessage")
    public JsonData flutterGetMessage(
                       @RequestBody promptMessage requestBody,
                       @RequestParam("chatUUID") String chatUUID,
                       @RequestAttribute("uuid") String userUUID,
                       @RequestParam("gptVersion") String gptVersion){
        logger.info("from wechat message - [chatUUID=[{}], uuid=[{}], gptVersion=[{}],"
                        + "requestBody=[\n{}\n] ",
                chatUUID,userUUID,gptVersion,requestBody);

        String response = this.chatService.getGPTMessage(userUUID,chatUUID,requestBody, GPTVersion.GPT40);
        logger.info(response);
        return JsonData.buildSuccess(response,"stop");
    }

}
