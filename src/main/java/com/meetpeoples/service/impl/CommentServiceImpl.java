package com.meetpeoples.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.exception.BadRequestException;
import com.meetpeoples.models.Comment;
import com.meetpeoples.models.Post;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.CommentRepository;
import com.meetpeoples.repository.PostRepository;
import com.meetpeoples.service.CommentService;
import com.meetpeoples.service.PostService;
import com.meetpeoples.service.UserService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Comment createComment(Comment comment, Long postId, Long userId) {
	
		User user = userService.findUserById(userId);
		  Post post = postService.findPostById(postId);
		  comment.setUser(user);
		  comment.setCreatedDate(LocalDateTime.now());
		  
		  Comment savedComment = commentRepository.save(comment);
		  
		  post.getComments().add(savedComment);
		  postRepository.save(post);
		return savedComment;
	}

	@Override
	public Comment findCommentById(Long commentId) {
		Optional<Comment> findById = commentRepository.findById(commentId);
		if(findById.isEmpty())
			 throw new BadRequestException("comment not exist");
		return findById.get();
	}

	@Override
	public Comment likeComment(Long commentId, Long userId) {
		 Comment comment = findCommentById(commentId);
		  User user = userService.findUserById(userId);
		  if(comment.getLiked().contains(user)) {
			  comment.getLiked().remove(user);
		  }else
			  comment.getLiked().add(user);
		return commentRepository.save(comment);
	}

}
