package com.meetpeoples.service;

import java.util.List;

import com.meetpeoples.models.Reel;
import com.meetpeoples.models.User;

public interface ReelService {

	public Reel createReel(Reel reel,User user);
	
	public List<Reel> findAllReels();
	
	public List<Reel> findUsersReels(Long userId);
}
