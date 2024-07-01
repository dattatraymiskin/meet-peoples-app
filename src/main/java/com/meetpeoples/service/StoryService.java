package com.meetpeoples.service;

import java.util.List;

import com.meetpeoples.models.Story;
import com.meetpeoples.models.User;

public interface StoryService {

	public Story createStory(Story story, User userId);
	
	public List<Story> findStoryByUserId(Long userId);
}
