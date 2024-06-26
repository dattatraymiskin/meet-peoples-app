package com.meetpeoples.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.dto.UserDTO;
import com.meetpeoples.exception.BadRequestException;
import com.meetpeoples.exception.UserNotFoundException;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.UserRepository;
import com.meetpeoples.service.UserService;
import com.meetpeoples.utility.ConversionUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ConversionUtility conversionUtility;

	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			modelMapper.map(userDTO, user);
			user.setId(userId);
			user = userRepository.save(user);
			return conversionUtility.convertToUserDtoWithoutPost(user);
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
		return conversionUtility.convertToUserDtoWithoutPost(user);
	}

	@Override
	public UserDTO findUserById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return conversionUtility.convertToDto(user.get());
		} else {
			throw new UserNotFoundException("User not found with id " + userId);
		}
	}

	@Override
	public UserDTO findUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return conversionUtility.convertToUserDtoWithoutPost(user.get());
		} /*
			 * else { throw new UserNotFoundException("User not found with email " + email);
			 * }
			 */
		return null;
	}

	@Override
	public UserDTO followUser(Long userId, Long followerId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		User follower = userRepository.findById(followerId)
				.orElseThrow(() -> new UserNotFoundException("Follower not found"));
		List<User> followers = user.getFollowers();
		if (!followers.contains(follower)) {
			followers.add(follower);
			follower.getFollowings().add(user);
			userRepository.save(user);
			// userRepository.save(follower);
			return conversionUtility.convertToUserDtoWithoutPost(user);
		} else {
			throw new BadRequestException("Already followed");
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
		List<UserDTO> userDTOs = new ArrayList<>();
		if (users != null) {
			for (User user : users) {
				userDTOs.add(conversionUtility.convertToUserDtoWithoutPost(user));
			}
			return userDTOs;
//	        return users.stream()
//	                     .map(user -> modelMapper.map(user, UserDTO.class))
//	                     .collect(Collectors.toList());
		} else
			throw new UserNotFoundException("Users not found");
	}

}
