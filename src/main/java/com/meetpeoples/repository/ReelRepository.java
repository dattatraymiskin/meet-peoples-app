package com.meetpeoples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetpeoples.models.Reel;

public interface ReelRepository extends JpaRepository<Reel, Long>{
	
	public List<Reel> findByUserId(Long userId);

}
