package com.meetpeoples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetpeoples.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	 List<Post> findByUserId(Long userId);
}