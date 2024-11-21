package com.wor.dash.jwt.model.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.wor.dash.user.model.User;

public interface JwtService {

	String extractUserEmail(String token);
	boolean isValid(String token, UserDetails user);
	boolean isValidRefreshToken(String token, User user);
	String generateAccessToken(User user);
	String generateRefreshToken(User user);
	int getUserIdFromToken(String token);
	String getUserEmailFromToken(String token);
	String getUserRoleFromToken(String token);

}
