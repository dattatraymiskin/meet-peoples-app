package com.meetpeoples.service;

import java.util.List;

import com.meetpeoples.models.Message;
import com.meetpeoples.models.User;

public interface MessageService {

	public Message createMessage(User user,Long chatId,Message message);
	
	public List<Message> findChatsMessages(Long chatId);
}
