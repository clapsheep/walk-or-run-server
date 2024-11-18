package com.wor.dash.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wor.dash.jwt.model.service.AuthenticationService;
import com.wor.dash.jwt.model.service.TokenService;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.user.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthenticationController {
	
	private final AuthenticationService authService;
	private final TokenService tokenRepository;
	
	public AuthenticationController(AuthenticationService authService, TokenService tokenRepository) {
		super();
		this.authService = authService;
		this.tokenRepository = tokenRepository;
	}
	
	@PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody User request
            ) {
    	try {
    		authService.register(request);
			return new ResponseEntity<ApiResponse>(new ApiResponse("success","register",200), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("fail","register",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User request
    ) {
    	try {
    		return ResponseEntity.ok(authService.authenticate(request));
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("fail","login",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
    	try {
    		 return  ResponseEntity.ok(authService.refreshToken(request, response)); 
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("fail","refreshToken",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
    }
}
