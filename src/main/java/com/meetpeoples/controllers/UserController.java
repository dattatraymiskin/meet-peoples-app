package com.meetpeoples.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.models.User;

@RestController
@RequestMapping("/users")
public class UserController {

	@GetMapping()
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		User user = new User(1L,"asa", "asas", "adad", null);
		users.add(user);
		return users;
	}
	
	@GetMapping("/user/{userId}")
	public User getUserById(@PathVariable(value = "userId") String userId) {
		List<User> users = new ArrayList<>();
		User user = new User(1L,"asa", "asas", "adad", null);
		User user2 = new User(2L,"asa", "asas", "adad", null);
		users.add(user);
		users.add(user2);
		
		return users.stream().filter(x -> userId.equals(x.getId())).findFirst().get();
	}
}
