package com.meetpeoples.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.exception.BadRequestException;
import com.meetpeoples.models.Chat;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.ChatRepository;
import com.meetpeoples.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	@Override
	public Chat createChat(User reqUser, User user) {
		
		 Chat alreadyExistChat = chatRepository.findChatByUsersId(user, reqUser);
		 
		 if(alreadyExistChat!=null) {
			 return alreadyExistChat;
			 
		 }
		 Chat chat = new Chat();
		 chat.getUsers().add(user);
		 chat.getUsers().add(reqUser);
		 chat.setTimeStamp(LocalDateTime.now());
		return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(Long chatId) {
		  Optional<Chat> findById = chatRepository.findById(chatId);
		  if(findById.isEmpty())
			  throw new BadRequestException("chat not found with chatId"+ chatId);
		  
		return findById.get();
	}

	@Override
	public List<Chat> findUsersChat(Long userId) {
		return chatRepository.findByUsersId(userId);
	}

	
}
