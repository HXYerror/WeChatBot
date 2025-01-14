package top.hxyac.chatbot.utils;

import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import top.hxyac.chatbot.config.StaticValue;
import top.hxyac.chatbot.model.GPTVersion;
import top.hxyac.chatbot.vo.azure.Gpt35Request;
import top.hxyac.chatbot.vo.azure.Gpt35Response;
import top.hxyac.chatbot.vo.wechat.AccessToken;
import top.hxyac.chatbot.vo.wechat.CodeSessionResp;
import top.hxyac.chatbot.vo.wechat.MsgCheckReq;
import top.hxyac.chatbot.vo.wechat.MsgCheckResp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestUtils {
    public static final String WECHAT_AUTH = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={jsCode}&grant_type=authorization_code";

    public static final String WECHAT_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";

    public static final String WECHAT_CHECK_MSG = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token={access_token}";

    public static final String WECHAT_HXY_OPENID = "oA_Pk4qIyBlK0TXmb2zSZUeR5s9o";

    private final static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    private final  static  String wechatKey = "wechatGet_";

    private final static String AZURE_API_KEY = "api-key";

    private static String AZURE_OPEN_AI_GPT4_O;
    private static String AZURE_OPEN_AI_GPT4;
    @Value("${hxyac.azure_gpt4_O}")
    public void setAZURE_OPEN_AI_GPT4_O(String SECRET) {
        RequestUtils.AZURE_OPEN_AI_GPT4_O = SECRET;
    }

    @Value("${hxyac.azure_gpt40}")
    public void setAZURE_OPEN_AI_GPT40(String SECRET) {
        RequestUtils.AZURE_OPEN_AI_GPT4 = SECRET;
    }

    //@Value("${hxyac.wechat.appid}")
    private String  APPID;

    //@Value("${hxyac.wechat.secret}")
    private String SECRET;

    @Value("${hxyac.azure.secret}")
    private String AZURE_SECRET;

    @Value("${hxyac.azure.gpt4.secret}")
    private String AZURE_GPT4_SECRET;

    @Autowired
    private RedisUtils redisUtil;

    /**
     *
     * @param gpt35Request
     * @param uuid
     * @return NULL or Gpt35Response if response is 200
     */
    public Gpt35Response getMessageFromAzureGPT35(Gpt35Request gpt35Request, GPTVersion version, String uuid){

        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);

        Map<String, String> params = new HashMap<>(10);
        Gpt35Response gpt35Response = null;
        ResponseEntity<Gpt35Response> response = null;


        HttpHeaders headers = new HttpHeaders();
        String RequestURL = AZURE_OPEN_AI_GPT4_O;
        String secret = AZURE_SECRET;
        switch (version){
            case GPT35:
                RequestURL = AZURE_OPEN_AI_GPT4_O;
                secret = AZURE_SECRET;
                break;
            case GPT40:
                RequestURL = AZURE_OPEN_AI_GPT4;
                secret = AZURE_GPT4_SECRET;
                break;
            case GPT4O:
                RequestURL = AZURE_OPEN_AI_GPT4_O;
                secret = AZURE_SECRET;
                break;
        }


        headers.set(AZURE_API_KEY,secret);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(gpt35Request,headers);

        logger.info(gpt35Request.toString());



        try{
            response = restTemplate.postForEntity(RequestURL,request,Gpt35Response.class,params);
        }catch (Exception e){
            logger.warn("postForEntity:["+uuid+"]"+" request chat from azure exception:"+e);
            return null;
        }

        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }else{
            logger.warn("Get-Message-Response:["+uuid+"]"+response.getStatusCode());
            return null;
        }

    }

    public  CodeSessionResp getWechatAuth(String code,String uuid){

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        Map<String, String> params = new HashMap<>(10);

        params.put("appid", StaticValue.APPID);
        params.put("secret", StaticValue.SECRET);
        params.put("jsCode",code);

        CodeSessionResp codeSessionResp = restTemplate.getForObject(WECHAT_AUTH, CodeSessionResp.class, params);

        System.out.println(codeSessionResp.toString());

        if( codeSessionResp != null){
            if(codeSessionResp.getErrcode() == null || (codeSessionResp.getErrcode() != null && codeSessionResp.getErrcode() == 0)) {
                return codeSessionResp;
            }else{
                logger.warn("CheckWarning:["+uuid+"]"+codeSessionResp);
                return null;
            }
        }
        return null;
    }


    public  String getWechatToken(){

        String accessToken = redisUtil.get(wechatKey + "accessToken",redisUtil.redisDb);

        if(StringUtils.isNotBlank(accessToken)){
            return accessToken;
        }else{
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<>(10);

            params.put("appid", APPID);
            params.put("secret", SECRET);

            AccessToken result = restTemplate.getForObject(WECHAT_TOKEN, AccessToken.class, params);

            if((result.getErrcode() == null && result.getAccess_token() != null)
            || (result.getErrcode() != null && result.getErrcode() == 0 && result.getAccess_token() != null)){
                redisUtil.set(wechatKey + "accessToken",result.getAccess_token(),redisUtil.redisDb);
                redisUtil.expire(wechatKey + "accessToken",result.getExpires_in() -300L,redisUtil.redisDb);
                return result.getAccess_token();
            }else{
                return null;
            }
        }
    }


    /**
     *
     * @param content
     * @param scene
     * @param openid
     * @param token
     * @return 0 is good
     */
    public Integer checkMsg(String content,Integer scene,String openid,String token,String uuid){

        System.out.println(openid);
        if(openid.startsWith("devopenid_")){
            openid = WECHAT_HXY_OPENID;
        }

        System.out.println(openid);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>(10);
        MsgCheckReq msgCheckReq = new MsgCheckReq(openid,scene,2,content);
        params.put("access_token", token);
        MsgCheckResp msgCheckResp = null;
        try{
            msgCheckResp = restTemplate.postForObject(WECHAT_CHECK_MSG,msgCheckReq, MsgCheckResp.class,params);
        }catch (Exception e){
            System.out.println(e);
        }

        if(msgCheckResp != null){
            if(((msgCheckResp.getErrcode() != null && msgCheckResp.getErrcode() == 0) || msgCheckResp.getErrcode() == null) && "pass".equals(msgCheckResp.getResultSuggest())) {
                return 0;
            }else{
                logger.warn("CheckWarning:["+uuid+"]"+msgCheckResp);
                return 1;
            }
        }
        return 2;
    }
}
