package top.hxyac.chatbot.service;

public interface UserService {

    /**
     * 根据密码和手机号查找用户
     * @param userName
     * @param userPwd
     * @return token信息
     */
    String selectUserByNameAndPwd(String userName,String userPwd);

    Boolean addUser(String userName,String userPwd);
}
