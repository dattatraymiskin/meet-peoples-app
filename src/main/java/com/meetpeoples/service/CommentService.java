package com.meetpeoples.service;

import com.meetpeoples.models.Comment;

public interface CommentService {

	
	public Comment createComment(Comment comment, Long postId, Long userId);

     public Comment findCommentById(Long commentId); 
     
     public Comment likeComment(Long commentId,Long userId);
     
     
}
