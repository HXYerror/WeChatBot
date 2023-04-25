package top.hxyac.chatbot.mapper;

import top.hxyac.chatbot.model.entity.Chat;
import org.apache.ibatis.annotations.Param;

public interface ChatMapper {
    int insertChatSql(@Param("chat") Chat chat);
}
