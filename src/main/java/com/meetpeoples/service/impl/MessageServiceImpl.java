package com.meetpeoples.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.models.Chat;
import com.meetpeoples.models.Message;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.ChatRepository;
import com.meetpeoples.repository.MessageRepository;
import com.meetpeoples.service.ChatService;
import com.meetpeoples.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRepository chatReposiroty;

	@Override
	public Message createMessage(User user, Long chatId, Message message) {

		Chat chat = chatService.findChatById(chatId);
		Message msg = new Message();
		msg.setChat(chat);
		msg.setContent(message.getContent());
		msg.setImage(message.getImage());
		msg.setUser(user);
		msg.setCreatedDate(LocalDateTime.now());
		Message saveMsg = messageRepository.save(msg);

		chat.getMessages().add(saveMsg);
		chatReposiroty.save(chat);
		return saveMsg;
	}

	@Override
	public List<Message> findChatsMessages(Long chatId) {
		chatService.findChatById(chatId);
		return messageRepository.findByChatId(chatId);
	}

}
