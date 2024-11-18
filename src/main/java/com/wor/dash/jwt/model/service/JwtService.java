package com.wor.dash.jwt.model.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.wor.dash.user.model.User;

public interface JwtService {
	
	public String extractUserEmail(String token);
	
	
	public boolean isValid(String token, UserDetails user);
	
	
	public boolean isValidRefreshToken(String token, User user);
	
	
	public String generateAccessToken(User user);
	
	
	public String generateRefreshToken(User user);

}
