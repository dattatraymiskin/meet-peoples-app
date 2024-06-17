package com.meetpeoples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetpeoples.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}