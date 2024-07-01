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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.dto.UserDTO;
import com.meetpeoples.models.User;
import com.meetpeoples.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

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

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "userId") Long userId) {
		UserDTO findUserById = userService.findUserById(userId);
		return ResponseEntity.ok(findUserById);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		UserDTO updatedUser = userService.updateUser(id, userDTO);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/follow/{followerId}")
	public ResponseEntity<Void> followUser(@PathVariable Long id, @PathVariable Long followerId) {
		userService.followUser(id, followerId);
		return ResponseEntity.ok().build();
	}
}
