package com.wor.dash.jwt.model.service;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wor.dash.jwt.model.Token;
import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.User;
import com.wor.dash.user.model.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserService repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final TokenService tokenRepository;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationServiceImpl(UserService repository,
						            PasswordEncoder passwordEncoder,
						            JwtService jwtService,
						            TokenService tokenRepository,
						            AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.tokenRepository = tokenRepository;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public AuthenticationResponse register(User request) {
		if(repository.findByUserEmail(request.getUserEmail()).isPresent()) {
            return new AuthenticationResponse(null, null,"User already exist");
        }

        User user = new User();
        user.setUserEmail(request.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));

        user.setUserRole(request.getUserRole());

        user = repository.addUser(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful");

	}

	@Override
	public AuthenticationResponse authenticate(User request) {
		authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getUserPassword()
                )
        );

        User user = repository.findByUserEmail(request.getUserEmail()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");
	}
	
	private void revokeAllTokenByUser(User user) {
		List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getUserId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLogout(1);
        });

        tokenRepository.addAllTokens(validTokens);
	}
	
	private void saveUserToken(String accessToken, String refreshToken, User user) {
		Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLogout(1);
        //token.setUser(user);
        token.setUserId(user.getUserId());
        token.setTokenId(null);
        tokenRepository.addToken(token);
	}

	@Override
	public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request,
			HttpServletResponse response) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract username from token
        String username = jwtService.extractUserEmail(token);

        // check if the user exist in database
        User user = repository.findByUserEmail(username)
                .orElseThrow(()->new RuntimeException("No user found"));

        // check if the token is valid
        if(jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

}