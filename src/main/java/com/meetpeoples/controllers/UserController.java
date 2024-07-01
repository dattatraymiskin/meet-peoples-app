package com.meetpeoples.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.dto.UserDTO;
import com.meetpeoples.models.User;
import com.meetpeoples.service.UserService;
import com.meetpeoples.utility.ConversionUtility;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ConversionUtility conversionUtility;

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
		UserDTO createdUser = userService.registerUser(user);
		return ResponseEntity.ok(createdUser);
	}

	@GetMapping()
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<UserDTO> users = userService.getUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token) {
		 User userFromJwt = userService.getUserFromJwt(token);
		 userFromJwt.setPassword(null);
		return ResponseEntity.ok(userFromJwt);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "userId") Long userId) {
		 User userById = userService.findUserById(userId);
		 UserDTO convertToDto = conversionUtility.convertToDto(userById);
		return ResponseEntity.ok(convertToDto);
	}

	@PutMapping("")
	public ResponseEntity<UserDTO> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserDTO userDTO) {
		User userFromJwt = userService.getUserFromJwt(token);
		UserDTO updatedUser = userService.updateUser(userFromJwt.getId(), userDTO);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/follow/{followerId}")
	public ResponseEntity<UserDTO> followUser(@RequestHeader("Authorization") String token, @PathVariable Long followerId) {
		User userFromJwt = userService.getUserFromJwt(token);
		UserDTO followUser = userService.followUser(userFromJwt.getId(), followerId);
		return ResponseEntity.ok(followUser);
	}
}
