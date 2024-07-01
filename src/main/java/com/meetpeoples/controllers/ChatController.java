package com.meetpeoples.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.models.Chat;
import com.meetpeoples.models.User;
import com.meetpeoples.request.CreateChatRequest;
import com.meetpeoples.service.ChatService;
import com.meetpeoples.service.UserService;

@RestController
@RequestMapping("v1/chats")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Chat> createChat(@RequestHeader("Authorization") String token,
			                    @RequestBody CreateChatRequest chatRequest) {
		User userFromJwt = userService.getUserFromJwt(token);
		User user = userService.findUserById(chatRequest.getUserId());
		Chat createChat = chatService.createChat(userFromJwt, user);
		return new ResponseEntity<>(createChat, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Chat>> findChatByUserId(@RequestHeader("Authorization") String token) {
		User userFromJwt = userService.getUserFromJwt(token);
	    List<Chat> findUsersChat = chatService.findUsersChat(userFromJwt.getId());
		return new ResponseEntity<>(findUsersChat, HttpStatus.OK);
	}
}
