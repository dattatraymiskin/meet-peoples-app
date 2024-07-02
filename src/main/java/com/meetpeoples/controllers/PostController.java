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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.dto.PostDTO;
import com.meetpeoples.models.Post;
import com.meetpeoples.models.User;
import com.meetpeoples.response.ApiResponse;
import com.meetpeoples.service.PostService;
import com.meetpeoples.service.UserService;
import com.meetpeoples.utility.ConversionUtility;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConversionUtility conversionUtility;

	@GetMapping()
	public ResponseEntity<List<PostDTO>> findAllPost() {
		List<PostDTO> posts = postService.findAllPost();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<PostDTO> createPostByUserId(@RequestHeader("Authorization") String token,@RequestBody Post post) {
		User userFromJwt = userService.getUserFromJwt(token);
		PostDTO createNewPost = postService.createNewPost(post, userFromJwt.getId());
		return new ResponseEntity<>(createNewPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{postId}/user")
	public ResponseEntity<ApiResponse> deletePostByUserId(@RequestHeader("Authorization") String token,
			     @PathVariable Long postId) {
		 User userFromJwt = userService.getUserFromJwt(token);
		String message = postService.deletePost(postId, userFromJwt.getId());
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}


	@PutMapping("/{postId}/user")
	public ResponseEntity<PostDTO> savePostByUserId(@RequestHeader("Authorization") String token,
			                  @PathVariable Long postId) {
		 User userFromJwt = userService.getUserFromJwt(token);
		PostDTO savedPost = postService.savedPost(postId, userFromJwt.getId());
		return new ResponseEntity<>(savedPost, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/like/{postId}/user")
	public ResponseEntity<PostDTO> likedPostByUserId(@RequestHeader("Authorization") String token,
			       @PathVariable Long postId) {
		 User userFromJwt = userService.getUserFromJwt(token);
		PostDTO savedPost = postService.likePost(postId, userFromJwt.getId());
		return new ResponseEntity<>(savedPost, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(value = "postId") Long postId) {
		Post postById = postService.findPostById(postId);
		PostDTO postDTO = conversionUtility.convertToDto(postById);
		return new ResponseEntity<>(postDTO, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDTO>> findUsersPost(@PathVariable(value = "userId") Long userId) {
		List<PostDTO> posts = postService.findPostByUserId(userId);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
}
