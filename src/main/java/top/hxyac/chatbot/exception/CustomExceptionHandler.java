package top.hxyac.chatbot.exception;


import top.hxyac.chatbot.config.EnvEnum;
import top.hxyac.chatbot.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 处理异常类
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @Value("${spring.profiles.active}")
    private String env;
    private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handle(Exception e){

        if(e instanceof CustomException){
            CustomException customException = (CustomException) e;
            logger.error("[GET ERROR:"+ customException.getUuid() + "]{msg:" + customException.getMsg() + ",code:"+customException.getCode()+"}");
            return JsonData.buildError(customException.getCode(),customException.getMsg());
        }else{
            if(e.getMessage() == null){
                logger.error("[GET ERROR]{msg:" + e.toString()+  "}");
            }else{
                logger.error("[GET ERROR]{msg:" + e.getMessage() + "}");
            }

            if(env.equals(EnvEnum.DEV.getEnv())){
                return JsonData.buildError("System throw error."+ e.getMessage());
            }else {
                return JsonData.buildError("System throw error.");
            }
        }
    }
}
