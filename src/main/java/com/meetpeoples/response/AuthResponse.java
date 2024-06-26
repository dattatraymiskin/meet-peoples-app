package com.meetpeoples.response;

import lombok.Data;

@Data
public class AuthResponse {
	
    private  String token;
    
    private String messge;

	public AuthResponse() {
		super();
	}

	public AuthResponse(String token, String messge) {
		super();
		this.token = token;
		this.messge = messge;
	}

    
  
}
