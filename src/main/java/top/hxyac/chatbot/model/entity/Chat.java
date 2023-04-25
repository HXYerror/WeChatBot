package top.hxyac.chatbot.model.entity;


/**
 * @Description  
 * @Author  hxy 
 * @Date 2022-03-18 
 */

public class Chat {

	private Integer chatId;

	private String chatUuid;

	private String chatSendUuid;

	private String chatReceiveUuid;

	private Long chatTime;

	private String chatContent;

	public Integer getChatId() {
		return this.chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public String getChatUuid() {
		return this.chatUuid;
	}

	public void setChatUuid(String chatUuid) {
		this.chatUuid = chatUuid;
	}

	public String getChatSendUuid() {
		return this.chatSendUuid;
	}

	public void setChatSendUuid(String chatSendUuid) {
		this.chatSendUuid = chatSendUuid;
	}

	public String getChatReceiveUuid() {
		return this.chatReceiveUuid;
	}

	public void setChatReceiveUuid(String chatReceiveUuid) {
		this.chatReceiveUuid = chatReceiveUuid;
	}

	public Long getChatTime() {
		return this.chatTime;
	}

	public void setChatTime(Long chatTime) {
		this.chatTime = chatTime;
	}

	public String getChatContent() {
		return this.chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

}
