package com.wor.dash.user.controller;

import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.MyChallenge;
import com.wor.dash.user.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Controller", description = "전반적인 유저 관련 기능을 관리합니다.(인증 포함)")
public class UserController {
	
	private final AuthenticationService authService;
	private final TokenService tokenRepository;
	private final UserService userService;

	@Operation(summary = "회원가입", description = "회원가입을 위한 API \n 이미 있는 경우는 400 반환 \n \n" +
			"<필수입력> \n " +
			"- userPassword : 비밀번호 \n " +
			"- userName : 유저 이름 \n " +
			"- userEmail : 이메일(아이디로 사용) \n" +
			"- userNickname : 닉네임 \n" +
			"- userPhoneNumber : 휴대폰 번호")
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

	@Operation(summary = "로그인", description = "로그인을 위한 API \n \n" +
			"<필수입력> \n " +
			"- userEmail : 아이디(이메일) \n " +
			"- userPassword : 비밀번호")
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

	@Operation(summary = "토큰 재발행", description = "권한 토큰 재발행을 위한 API \n \n")
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

	@Operation(summary = "마이페이지 기능", description = "유저의 개인정보 조회를 위한 API \n \n" +
			"<필수입력> \n " +
			"- userId : User table의 Primary Key")
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

	@Operation(summary = "유저 권한 변경", description = "유저 관리자 설정을 위한 API(관리자만 가능) \n \n" +
			"<필수입력> \n " +
			"- userId : User table의 Primary Key")
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

	@Operation(summary = "내 정보 변경", description = "개인정보 변경을 위한 API \n \n")
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

	@Operation(summary = "참여한 챌린지 목록 반환 기능", description = "유저가 참여한 챌린지 목록 조회를 위한 API \n \n" +
			"<필수입력> \n " +
			"- userId : User table의 Primary Key")
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

	@Operation(summary = "탈퇴 기능", description = "유저 탈퇴를 위한 API")
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

	// 비밀번호로 확인 후 변경하는 로직 추가 필요
	@PostMapping("/checkPw")
	public ResponseEntity<?> checkPw(
			@RequestBody User user,
			@RequestHeader("Authorization") String authHeader) {
		log.debug("AuthenticationController/checkPw");
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(null, null, "Unauthorized Token"), HttpStatus.UNAUTHORIZED);
		}

//		try {
////			Optional<String>
//		}
		return new ResponseEntity<ApiResponse> (new ApiResponse("success", "checkPw", 200), HttpStatus.OK);
	}
}
