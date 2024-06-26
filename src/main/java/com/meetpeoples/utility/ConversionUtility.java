package com.meetpeoples.utility;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.dto.PostDTO;
import com.meetpeoples.dto.UserDTO;
import com.meetpeoples.models.Post;
import com.meetpeoples.models.User;

@Service
public class ConversionUtility {
	    @Autowired
	    private ModelMapper modelMapper;

	    public UserDTO convertToDto(User user) {
			if (user != null) {
				UserDTO userDTO = modelMapper.map(user, UserDTO.class);
				userDTO.setPosts(user.getPosts().stream()
						.map(this::convertToDto).collect(Collectors.toList()));
				return userDTO;
			} else
				return null;
	    }
	    
	    public UserDTO convertToUserDtoWithoutPost(User user) {
	    	if (user != null) {
	    		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
	    		userDTO.setPosts(null);
	    		return userDTO;
	    	}else
	    		return null;
	    }

	    public PostDTO convertToDto(Post post) {
	        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
	        postDTO.setUser(null); // Avoid cyclic dependency
	        return postDTO;
	    }
}
