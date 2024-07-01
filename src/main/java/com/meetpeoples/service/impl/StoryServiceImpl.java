package com.meetpeoples.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.exception.BadRequestException;
import com.meetpeoples.models.Story;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.StoryRepository;
import com.meetpeoples.service.StoryService;
import com.meetpeoples.service.UserService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Story createStory(Story story, User user) {

		if (story == null)
			throw new BadRequestException("Story can't empty");

		story.setUser(user);
		story.setCreatedDate(LocalDateTime.now());

		return storyRepository.save(story);
	}

	@Override
	public List<Story> findStoryByUserId(Long userId) {
		User user = userService.findUserById(userId);
		List<Story> list = storyRepository.findByUserId(user.getId());
		return list;
	}
}
