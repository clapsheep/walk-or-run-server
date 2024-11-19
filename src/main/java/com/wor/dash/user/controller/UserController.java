package com.wor.dash.user.controller;

import com.wor.dash.response.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class UserController {
	
	private final AuthenticationService authService;
	private final TokenService tokenRepository;
	
	public
	UserController(AuthenticationService authService, TokenService tokenRepository) {
		super();
		this.authService = authService;
		this.tokenRepository = tokenRepository;
	}
	
	@PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody User request
            ) {
		log.debug("AuthController/register 컨트롤러 회원가입");
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
    	log.debug("AuthenticationController/login");
    	try {
			AuthenticationResponse response = authService.authenticate(request);
    		if(response.getAccessToken() != null) return ResponseEntity.ok(response);
			else return new ResponseEntity<ApiResponse>(new ApiResponse("withdrawn user", "login", 403), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("fail","login",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping("/refresh")
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
