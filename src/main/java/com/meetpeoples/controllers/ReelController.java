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

import com.meetpeoples.models.Reel;
import com.meetpeoples.models.User;
import com.meetpeoples.service.ReelService;
import com.meetpeoples.service.UserService;

@RestController
@RequestMapping("/v1/reels")
public class ReelController {

	@Autowired
	private ReelService reelService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	public ResponseEntity<Reel> createPostByUserId(@RequestHeader("Authorization") String token,
			                                     @RequestBody Reel reel) {
		User userFromJwt = userService.getUserFromJwt(token);
		Reel createReel = reelService.createReel(reel, userFromJwt);
		return new ResponseEntity<>(createReel, HttpStatus.ACCEPTED);
	}
	
	@GetMapping()
	public ResponseEntity<List<Reel>> findAllReels() {
		List<Reel> reels = reelService.findAllReels();
		return new ResponseEntity<>(reels, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<List<Reel>> findUsersReels(@PathVariable Long userId) {
		List<Reel> reels = reelService.findUsersReels(userId);
		return new ResponseEntity<>(reels, HttpStatus.ACCEPTED);
	}
}
