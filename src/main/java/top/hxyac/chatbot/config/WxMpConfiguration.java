package top.hxyac.chatbot.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.hxyac.chatbot.handler.MsgHandler;


@RequiredArgsConstructor
@Configuration
public class WxMpConfiguration {

    private final MsgHandler msgHandler;

    @Value("${wx.mp.configs.appId}")
    private String appId;

    @Value("${wx.mp.configs.secret}")
    private String secret;

    @Value("${wx.mp.configs.token}")
    private String token;

    @Value("${wx.mp.configs.aeskey}")
    private String aesKey;

    @Bean
    public WxMpService wxMpService() {
        WxMpService service = new WxMpServiceImpl();
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(appId);
        configStorage.setSecret(secret);
        configStorage.setToken(token);
        configStorage.setAesKey(aesKey);
        service.addConfigStorage(appId,configStorage);
        return service;
    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }

}

