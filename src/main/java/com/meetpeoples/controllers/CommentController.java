package com.meetpeoples.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.models.Comment;
import com.meetpeoples.models.User;
import com.meetpeoples.service.CommentService;
import com.meetpeoples.service.UserService;

@RestController
@RequestMapping("/v1/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<Comment> createComment(@RequestHeader("Authorization") String token,
			                                        @RequestBody Comment comment,
			                                        @PathVariable Long postId) {
		User userFromJwt = userService.getUserFromJwt(token);
		 Comment createComment = commentService.createComment(comment, postId, userFromJwt.getId());
		return new ResponseEntity<>(createComment, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/like/{commentId}")
	public ResponseEntity<Comment> likeComment(@RequestHeader("Authorization") String token,
			                                        @PathVariable Long commentId) {
		  User userFromJwt = userService.getUserFromJwt(token);
		 Comment likedComment = commentService.likeComment(commentId, userFromJwt.getId());
		return new ResponseEntity<>(likedComment, HttpStatus.ACCEPTED);
	}
}
