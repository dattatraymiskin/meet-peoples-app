package com.meetpeoples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetpeoples.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
}
