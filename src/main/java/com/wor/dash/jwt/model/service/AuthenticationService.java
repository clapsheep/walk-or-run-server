package com.wor.dash.jwt.model.service;

import org.springframework.http.ResponseEntity;

import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
	
	public AuthenticationResponse register(User request);
	public AuthenticationResponse authenticate(User request);
	public void revokeAllTokenByUser(User user);
	public void saveUserToken(String accessToken, String refreshToken, User user);
	public ResponseEntity<AuthenticationResponse> refreshToken(
			HttpServletRequest request,
			HttpServletResponse response);
}
