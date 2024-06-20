package com.meetpeoples.service;

import java.util.List;

import com.meetpeoples.models.Post;

public interface PostService {

	Post createNewPost(Post post, Long userId);
	
	String deletePost(Long postId, Long userId);
	
	List<Post> findPostByUserId(Long userId);
	
	Post findPostById(Long postId);
	
	List<Post> findAllPost();
	
	Post savedPost(Long postId, Long userId);
	
	Post likePost(Long postId,Long userId);

	
}
