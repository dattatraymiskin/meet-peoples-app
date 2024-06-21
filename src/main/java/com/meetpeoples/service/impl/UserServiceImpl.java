package com.meetpeoples.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.dto.UserDTO;
import com.meetpeoples.exception.UserNotFoundException;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.UserRepository;
import com.meetpeoples.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			modelMapper.map(userDTO, user);
			user.setId(userId);
			user = userRepository.save(user);
			return modelMapper.map(user, UserDTO.class);
		} else {
			throw new UserNotFoundException("User not found with id " + userId);
		}
	}

	public void deleteUser(Long userId) {
		if (userRepository.existsById(userId)) {
			userRepository.deleteById(userId);
		} else {
			throw new UserNotFoundException("User not found with id " + userId);
		}
	}

	@Override
	public UserDTO registerUser(User user) {
		user = userRepository.save(user);
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public UserDTO findUserById(Long userId) {
		Optional<User> findById = userRepository.findById(userId);
		if (findById.isPresent()) {
			return modelMapper.map(findById.get(), UserDTO.class);
		} else {
			throw new UserNotFoundException("User not found with id " + userId);
		}
	}

	@Override
	public UserDTO findUserByEmail(String email) {
		Optional<User> findByEmail = userRepository.findByEmail(email);
		if (findByEmail.isPresent()) {
			return modelMapper.map(findByEmail.get(), UserDTO.class);
		} else {
			throw new UserNotFoundException("User not found with email " + email);
		}
	}

	@Override
	public UserDTO followUser(Long userId, Long followerId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> followerOptional = userRepository.findById(followerId);
        if (userOptional.isPresent() && followerOptional.isPresent()) {
            User user = userOptional.get();
            User follower = followerOptional.get();
            user.getFollowers().add(follower);
            follower.getFollowings().add(user);
            userRepository.save(user);
            userRepository.save(follower);
        	return modelMapper.map(user, UserDTO.class);
        } else {
            throw new UserNotFoundException("User not found with id " + userId + " or follower id " + followerId);
        }
	}

	@Override
	public List<UserDTO> searchUser(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getUsers() {
		 List<User> users = userRepository.findAll();
		  if(users!=null) {
	        return users.stream()
	                     .map(user -> modelMapper.map(user, UserDTO.class))
	                     .collect(Collectors.toList());
		  }else
			  throw new UserNotFoundException("Users not found");
	}
	

	// Other CRUD operations
}
