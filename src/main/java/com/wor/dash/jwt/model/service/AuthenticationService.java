package com.wor.dash.jwt.model.service;


import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.User;

public interface AuthenticationService {

	AuthenticationResponse register(User request);
	AuthenticationResponse authenticate(User request);
	AuthenticationResponse refreshToken(
			String userEmail, String authHeader);
}
