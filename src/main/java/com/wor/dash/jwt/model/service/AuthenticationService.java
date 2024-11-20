package com.wor.dash.jwt.model.service;

import org.springframework.http.ResponseEntity;

import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
	
	public AuthenticationResponse register(User request);
	public AuthenticationResponse authenticate(User request);
	public AuthenticationResponse refreshToken(
			String userEmail, String authHeader);
}
