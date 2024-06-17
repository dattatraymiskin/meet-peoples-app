package com.meetpeoples.service.impl;

import java.util.List;
import java.util.Optional;

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
			user.setUsername(userDTO.getUsername());
			user.setEmail(userDTO.getEmail());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO followUser(Long userId1, Long userId2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> searchUser(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	

	// Other CRUD operations
}
