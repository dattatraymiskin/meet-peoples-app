package com.meetpeoples.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meetpeoples.config.JwtUtil;
import com.meetpeoples.dto.UserDTO;
import com.meetpeoples.exception.UserNotFoundException;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.UserRepository;
import com.meetpeoples.request.LoginRequest;
import com.meetpeoples.response.AuthResponse;
import com.meetpeoples.service.UserService;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUp(@RequestBody User user) {
		
		  UserDTO userDto = userService.findUserByEmail(user.getEmail());
		  if(userDto == null) {
		      user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		      User savedUser = userRepository.save(user);
		      
		      Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		               
		        final UserDetails userDetails = userDetailsService
		                .loadUserByUsername(user.getEmail());

		        final String jwt = jwtUtil.generateToken(userDetails);
		        AuthResponse authResponse = new AuthResponse(jwt, "Jwt token created successfully");
		     // jwtUtil.generateToken(authentication);
		        return ResponseEntity.ok(authResponse);
		  }else
			  throw new UserNotFoundException("User already present with email " + user.getEmail());
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
		
		UserDetails userByUsername = userDetailsService.loadUserByUsername(loginRequest.getEmail());
		if(userByUsername== null) {
			throw new BadCredentialsException("invalid email");
		}
		
		if(!passwordEncoder.matches(loginRequest.getPassword(), userByUsername.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}
	       //Authentication authentication = new UsernamePasswordAuthenticationToken(userByUsername, userByUsername.getAuthorities());
			//  throw new UserNotFoundException("User already present with email " + user.getEmail());
	
	      final String jwt = jwtUtil.generateToken(userByUsername);
	        AuthResponse authResponse = new AuthResponse(jwt, "Jwt token created successfully");
	     // jwtUtil.generateToken(authentication);
	        return ResponseEntity.ok(authResponse);
	
	}
	

}
