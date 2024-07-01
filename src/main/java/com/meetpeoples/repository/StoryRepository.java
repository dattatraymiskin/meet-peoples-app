package com.meetpeoples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetpeoples.models.Story;

public interface StoryRepository  extends JpaRepository<Story, Long>{

	public List<Story> findByUserId(Long userId);
}
