package com.meetpeoples.service;

import java.util.List;

import com.meetpeoples.dto.PostDTO;
import com.meetpeoples.models.Post;

public interface PostService {

	PostDTO createNewPost(Post post, Long userId);
	
	String deletePost(Long postId, Long userId);
	
	List<PostDTO> findPostByUserId(Long userId);
	
	Post findPostById(Long postId);
	
	List<PostDTO> findAllPost();
	
	PostDTO savedPost(Long postId, Long userId);
	
	PostDTO likePost(Long postId,Long userId);

	
}
