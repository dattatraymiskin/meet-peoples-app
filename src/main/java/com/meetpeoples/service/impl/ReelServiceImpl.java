package com.meetpeoples.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.exception.BadRequestException;
import com.meetpeoples.models.Reel;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.ReelRepository;
import com.meetpeoples.service.ReelService;
import com.meetpeoples.service.UserService;

@Service
public class ReelServiceImpl implements ReelService{

	@Autowired
	private ReelRepository reelRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Reel createReel(Reel reel, User user) {
		if(reel == null) {
			throw new BadRequestException("reel is empty");
		}
	    reel.setUser(user);
		return reelRepository.save(reel);
	}

	@Override
	public List<Reel> findAllReels() {
		return reelRepository.findAll();
	}

	@Override
	public List<Reel> findUsersReels(Long userId) {
		userService.findUserById(userId);
		return reelRepository.findByUserId(userId);
	}

}
