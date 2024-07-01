package com.meetpeoples.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.models.Story;
import com.meetpeoples.models.User;
import com.meetpeoples.service.StoryService;
import com.meetpeoples.service.UserService;

@RestController
@RequestMapping("v1/story")
public class StoryController {

	
	@Autowired
	private StoryService storyService;
	
	@Autowired
	private UserService userService;
	

	@PostMapping()
	public ResponseEntity<Story> createStory(@RequestHeader("Authorization") String token,
			            @RequestBody Story story){
		User user = userService.getUserFromJwt(token);
		Story createStory = storyService.createStory(story, user);
		return ResponseEntity.accepted().body(createStory);
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<List<Story>> findStoryByUserId(@PathVariable Long userId){
		userService.findUserById(userId);
		List<Story> list = storyService.findStoryByUserId(userId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
