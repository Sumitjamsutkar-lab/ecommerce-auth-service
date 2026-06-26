package com.ecommerce.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthResponse {

    

	public AuthResponse(String token) {
		super();
		this.token = token;
		System.out.println("token"+token);
	}

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}