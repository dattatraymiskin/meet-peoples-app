package com.meetpeoples.service;

import java.util.List;

import com.meetpeoples.dto.UserDTO;
import com.meetpeoples.models.User;

public interface UserService {

	public UserDTO registerUser(User user);
	
	public User findUserById(Long userId);
	
	public UserDTO findUserByEmail(String email);
	
	public UserDTO followUser(Long reqUserId,Long userId2);
	
	public UserDTO updateUser(Long userId, UserDTO userDTO);
	
	public List<UserDTO> searchUser(String query);
	
	public void deleteUser(Long userId);
	
	public List<UserDTO> getUsers();
	
	public User getUserFromJwt(String token);
}
