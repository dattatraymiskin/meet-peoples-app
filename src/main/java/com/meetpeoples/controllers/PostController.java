package com.meetpeoples.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.models.Post;
import com.meetpeoples.response.ApiResponse;
import com.meetpeoples.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping()
	public ResponseEntity<List<Post>> findAllPost() {
		List<Post> posts = postService.findAllPost();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<Post> createPostByUserId(@RequestBody Post post, @PathVariable Long userId) {
		Post createNewPost = postService.createNewPost(post, userId);
		return new ResponseEntity<>(createNewPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{postId}/user/{userId}")
	public ResponseEntity<ApiResponse> deletePostByUserId(@PathVariable Long postId, @PathVariable Long userId) {
		String message = postService.deletePost(postId, userId);
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable(value = "postId") Long postId) {
		Post findPostById = postService.findPostById(postId);
		return new ResponseEntity<>(findPostById, HttpStatus.ACCEPTED);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Post>> findUsersPost(@PathVariable(value = "userId") Long userId) {
		List<Post> posts = postService.findPostByUserId(userId);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@PutMapping("/{postId}/user/{userId}")
	public ResponseEntity<Post> savePostByUserId(@PathVariable Long postId, @PathVariable Long userId) {
		 Post savedPost = postService.savedPost(postId, userId);
		return new ResponseEntity<>(savedPost, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/like/{postId}/user/{userId}")
	public ResponseEntity<Post> likedPostByUserId(@PathVariable Long postId, @PathVariable Long userId) {
		 Post savedPost = postService.likePost(postId, userId);
		return new ResponseEntity<>(savedPost, HttpStatus.ACCEPTED);
	}
}
