package top.hxyac.chatbot.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.hxyac.chatbot.exception.CustomException;
import top.hxyac.chatbot.model.EntityEnum;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.hxyac.chatbot.utils.JWTUtils;
import top.hxyac.chatbot.utils.JsonData;
import top.hxyac.chatbot.utils.RedisUtils;
import top.hxyac.chatbot.utils.ResultEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hxy
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    private final RedisUtils redisUtils;

    public LoginInterceptor(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 进入到controller之前的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = (String) request.getAttribute("userUUID");
        try{
            String accessToken =  request.getHeader("token");
            if(StringUtils.isNotBlank(accessToken)){

                Claims claims = JWTUtils.checkJWT(accessToken);
                if(claims == null){
                    logger.info("GetInfo:"+ ResultEnum.USER_NOT_LOGIN.getMsg());
                    sendJsonMessage(response, JsonData.buildError(ResultEnum.USER_NOT_LOGIN.getCode(),ResultEnum.USER_NOT_LOGIN.getMsg()));
                    return false;
                }

                String userUuid = (String)claims.get("uuid");
                request.setAttribute("uuid",userUuid);

//                Boolean hasToken =  redisUtils.hexists(redisUtils.prefix + RedisUtils.tokenKey,userUuid);
//                if(hasToken){
//                    String nowToken = redisUtils.hget(redisUtils.prefix + RedisUtils.tokenKey,userUuid);
//                    if(!nowToken.equals(accessToken)){
//                        sendJsonMessage(response, JsonData.buildError(ResultEnum.USER_NOT_LOGIN.getCode(),ResultEnum.USER_NOT_LOGIN.getMsg()));
//                        return false;
//                    }
//                }else{
//                    sendJsonMessage(response, JsonData.buildError(ResultEnum.USER_NOT_LOGIN.getCode(),ResultEnum.USER_NOT_LOGIN.getMsg()));
//                    return false;
//                }
//
//
//                Integer userDisable = (Integer) claims.get("userDisable");
//                Integer userAdmin = (Integer) claims.get("userAdmin");
//                request.setAttribute("userAdmin",userAdmin);
//
//                if(userDisable == null || userDisable.equals(EntityEnum.Disable.getValue())){
//                    logger.info("GetInfo:["+uuid+"]"+ ResultEnum.USER_BAN.getMsg());
//                    sendJsonMessage(response, JsonData.buildError(ResultEnum.USER_BAN.getCode(),ResultEnum.USER_BAN.getMsg()));
//                    return false;
//                }

                return true;
            }
        } catch (Exception e){
            throw new CustomException(ResultEnum.USER_TOKEN_ERROR.getCode(),ResultEnum.USER_TOKEN_ERROR.getMsg(),uuid);
        }

        logger.info("GetInfo:["+uuid+"]"+ResultEnum.USER_TOKEN_ERROR.getMsg());
        sendJsonMessage(response, JsonData.buildError(ResultEnum.USER_TOKEN_ERROR.getCode(),ResultEnum.USER_TOKEN_ERROR.getMsg()));
        return false;
    }

    public static void sendJsonMessage(HttpServletResponse response,Object obj){

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
