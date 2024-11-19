package com.wor.dash.user.controller;

import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.MyChallenge;
import com.wor.dash.user.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wor.dash.jwt.model.service.AuthenticationService;
import com.wor.dash.jwt.model.service.TokenService;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.user.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
	
	private final AuthenticationService authService;
	private final TokenService tokenRepository;
	private final UserService userService;
	
	@PostMapping("/auth/register")
    public ResponseEntity<?> register(
            @RequestBody User request
            ) {
		log.debug("AuthController/register 컨트롤러 회원가입");
    	try {
			AuthenticationResponse response = authService.register(request);
			if(response.getAccessToken() != null) return new ResponseEntity<ApiResponse>(new ApiResponse("success","register",201), HttpStatus.CREATED);
			else return new ResponseEntity<ApiResponse>(new ApiResponse("User Already Exists", "register", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("fail","register",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping("/auth/login")
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

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
		log.debug("AuthenticationController/refreshToken");
    	try {
    		 return  ResponseEntity.ok(authService.refreshToken(request, response)); 
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("fail","refreshToken",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserInfo(@PathVariable("userId") int userId) {
		log.debug("AuthenticationController/getUserInfo");
		try {
			Optional<User> challenges = userService.getPublicInfo(userId);
			log.debug(challenges.toString());
			if(challenges.isPresent()) {
				return new ResponseEntity<User>(challenges.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse("empty","getUserInfo",204), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse> (new ApiResponse("fail","getUserInfo",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<?> updateUserRole(@PathVariable("userId") int userId) {
		log.debug("AuthenticationController/updateUserRole");
		try {
			Optional<Integer> change = userService.updateUserRole(userId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("success", "updateUserRole", 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse> (new ApiResponse("fail","updateUserRole",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUserInfo(@RequestBody User user) {
		log.debug("AuthenticationController/updateUserInfo");
		try {
			Optional<Integer> change = userService.updateUserInfo(user);
			return new ResponseEntity<ApiResponse> (new ApiResponse("success", "modifyUserInfo", 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse> (new ApiResponse("fail","modifyUserInfo",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/challenge/{userId}")
	public ResponseEntity<?> getChallenges(@PathVariable("userId") int userId) {
		log.debug("AuthenticationController/getChallenges");
		try {
			Optional<List<MyChallenge>> challenges = userService.getChallenges(userId);
			if(challenges.isPresent()) {
				return new ResponseEntity<List<MyChallenge>> (challenges.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiResponse> (new ApiResponse("empty", "getChallenges", 204), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse> (new ApiResponse("fail","getChallenges",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestBody User user) {
		log.debug("AuthenticationController/withdraw");
		try {
			Optional<Integer> delete = userService.withdrawUser(user.getUserId());
			return new ResponseEntity<ApiResponse> (new ApiResponse("success", "withdraw", 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse> (new ApiResponse("fail","withdraw",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
