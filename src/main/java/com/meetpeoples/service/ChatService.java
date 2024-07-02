package com.meetpeoples.service;

import java.util.List;

import com.meetpeoples.models.Chat;
import com.meetpeoples.models.User;

public interface ChatService {

	public Chat createChat(User reqUser,User user);
	
	public Chat findChatById(Long chatId);
	
	public List<Chat> findUsersChat(Long userId);
}
