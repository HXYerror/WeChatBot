package top.hxyac.chatbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.hxyac.chatbot.model.entity.MMessage;
import top.hxyac.chatbot.model.entity.User;
import top.hxyac.chatbot.service.UserService;
import top.hxyac.chatbot.utils.CommonUtils;
import top.hxyac.chatbot.utils.JWTUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String selectUserByNameAndPwd(String userName, String userPwd) {

        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(userName));
        User user =  this.mongoTemplate.findOne(query, User.class, "flutter_user");
        logger.info(userName + userPwd + query.toString());
        if(user == null || !userPwd.equals(user.getUserPwd())){
            return null;
        }else{
            String token = JWTUtils.geneJsonWebToken(user.getUuid());
            return token;
        }

    }

    @Override
    public Boolean addUser(String userName, String userPwd) {
        User user = new User();
        user.setUserPwd(userPwd);
        user.setUserName(userName);
        user.setUuid(CommonUtils.UUID());

        User tryUser = this.mongoTemplate.save(user, "flutter_user");
        if(tryUser.getId() != null){
            return true;
        }else{
            return false;
        }
    }
}
