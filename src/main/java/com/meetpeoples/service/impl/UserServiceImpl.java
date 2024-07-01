package com.meetpeoples.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.config.JwtUtil;
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
	private JwtUtil jwtUtil;
	
	@Autowired	
	private ConversionUtility conversionUtility;

	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			//modelMapper.map(userDTO, user);
			if(userDTO.getFirstName()!=null)
				user.setFirstName(userDTO.getFirstName());
			if(userDTO.getLastName()!=null)
				user.setLastName(userDTO.getLastName());
			if(userDTO.getUsername()!=null)
				user.setUsername(userDTO.getUsername());
			
			if(userDTO.getGender()!=null)
				user.setGender(userDTO.getGender());
			
			if(userDTO.getEmail()!=null)
				user.setEmail(userDTO.getEmail());
			
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
	public User findUserById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			  return user.get();
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
	public UserDTO followUser(Long reqUserId, Long followerId) {
		User ReqUser = userRepository.findById(reqUserId).orElseThrow(() -> new UserNotFoundException("User not found"));
		User follower = userRepository.findById(followerId)
				.orElseThrow(() -> new UserNotFoundException("Follower not found"));
		List<User> followers = ReqUser.getFollowers();
		if (!followers.contains(follower)) {
			followers.add(follower);
			follower.getFollowings().add(ReqUser);
			userRepository.save(ReqUser);
			// userRepository.save(follower);
			return conversionUtility.convertToUserDtoWithoutPost(ReqUser);
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

	@Override
	public User getUserFromJwt(String token) {
		if(token!=null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		String email = jwtUtil.extractEmailFromJwt(token);
		Optional<User> findByEmail = userRepository.findByEmail(email);
		if(findByEmail.isPresent())
			 return findByEmail.get();
		else
			throw new UserNotFoundException("Users not found");
		
	}

}
