package top.hxyac.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.hxyac.chatbot.service.UserService;
import top.hxyac.chatbot.utils.JsonData;
import top.hxyac.chatbot.vo.request.LoginRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    private final UserService userService;

    @Value("${spring.profiles.active}")
    private String env;

    @Value("${hxyac.register.pwd}")
    private String signaturePri;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public JsonData login(@RequestBody LoginRequest loginRequest){

        String token = userService.selectUserByNameAndPwd(loginRequest.getUserName(),loginRequest.getUserPwd());

        return token == null ? JsonData.buildError("登陆失败") : JsonData.buildSuccess((Object) token);
    }


    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    @PostMapping("register")
    public JsonData addUser(@RequestBody LoginRequest loginRequest, @RequestParam("signature") String signature){
        if(!signature.equals(signaturePri)){
            return JsonData.buildError("注册失败");
        }

        Boolean result = userService.addUser(loginRequest.getUserName(),loginRequest.getUserPwd());

        return result ? JsonData.buildSuccess() : JsonData.buildError("注册失败") ;
    }
}
