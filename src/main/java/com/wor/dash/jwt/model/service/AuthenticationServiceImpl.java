package com.wor.dash.jwt.model.service;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wor.dash.jwt.model.Token;
import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.User;
import com.wor.dash.user.model.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
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
//	@Transactional
	public AuthenticationResponse register(User request) {
		log.debug("AuthenticationServiceImpl/register");
        log.debug("request: {}", request.getUserEmail());
		if(repository.getUserId(request.getUserEmail()).isPresent()) {
            log.debug("user id is already exist");
            return new AuthenticationResponse(null, null,"User already exist");
        }

        User user = new User();
        user.setUserEmail(request.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setUserName(request.getUserName());
        user.setUserNickname(request.getUserNickname());
        user.setUserPhoneNumber(request.getUserPhoneNumber());

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

        User user = repository.getPublicInfo(request.getUserId()).get();
        if(user.getUserWithdrawalStatus() == 1) {
            return new AuthenticationResponse(null, null, "Withdrawn User");
        }
		String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");
	}
	
	public void revokeAllTokenByUser(User user) {
		List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getUserId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLogout(1);
        });

        tokenRepository.addAllTokens(validTokens);
	}

    public void saveUserToken(String accessToken, String refreshToken, User user) {
		Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLogout(1);
        token.setUserId(user.getUserId());
        token.setTokenId(null);
        tokenRepository.addToken(token);
	}

	@Override
	public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request,
			HttpServletResponse response) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(null, null, "Unauthorized Token"), HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        String userEmail = jwtService.extractUserEmail(token);
        int userId = repository.getUserId(userEmail).get();

        User user = repository.getPublicInfo(userId)
                .orElseThrow(()->new RuntimeException("No user found"));

        if(jwtService.isValidRefreshToken(token, user)) {
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(null, null, "Unauthorized Token"), HttpStatus.UNAUTHORIZED);
	}

}
