package com.meetpeoples.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.models.Message;
import com.meetpeoples.models.User;
import com.meetpeoples.service.MessageService;
import com.meetpeoples.service.UserService;

@RestController
@RequestMapping("/v1/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@PostMapping("/chat/{chatId}")
	public ResponseEntity<Message> createMessage(@RequestHeader("Authorization") String token,
			@RequestBody Message message, @PathVariable Long chatId) {
		User user = userService.getUserFromJwt(token);
		Message createMessage = messageService.createMessage(user, chatId, message);
		return new ResponseEntity<>(createMessage, HttpStatus.OK);
	}

	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> findChatMessage(@RequestHeader("Authorization") String token,
			@PathVariable Long chatId) {
		userService.getUserFromJwt(token);
		List<Message> messages = messageService.findChatsMessages(chatId);
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
}
