package com.wor.dash.user.controller;

import com.wor.dash.jwt.model.service.JwtService;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.MyChallenge;
import com.wor.dash.user.model.service.UserService;
import com.wor.dash.userGoal.model.UserGoal;
import com.wor.dash.userGoal.model.service.UserGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wor.dash.jwt.model.service.AuthenticationService;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.user.model.User;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "User Controller", description = "전반적인 유저 관련 기능을 관리합니다.(인증 포함)")
public class UserController {

	private final AuthenticationService authService;
	private final JwtService jwtService;
	private final UserService userService;
	private final UserGoalService userGoalService;

	@Operation(summary = "회원가입", description = "회원가입을 위한 API \n" +
			" 이미 있는 경우는 400 반환합니다 \n" +
			" \n" +
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
		log.info("UserController/register 컨트롤러 회원가입");
		try {
			AuthenticationResponse response = authService.register(request);
			if(response.getAccessToken() != null) return new ResponseEntity<>(new ApiResponse("success","register",201), HttpStatus.CREATED);
			else return new ResponseEntity<>(new ApiResponse("User Already Exists", "register", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ApiResponse("fail","register",500), HttpStatus.INTERNAL_SERVER_ERROR);
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
		log.info("UserController/login");
		try {
			AuthenticationResponse response = authService.authenticate(request);
			if(response.getAccessToken() != null) return ResponseEntity.ok(response);
			else return new ResponseEntity<>(new ApiResponse("withdrawn user", "login", 403), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ApiResponse("fail","login",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "토큰 재발행", description = "권한 토큰 재발행을 위한 API \n \n")
	@PostMapping("/auth/user/{userId}/refresh")
	public ResponseEntity<?> refreshToken(
			@PathVariable("userId") int userId,
			@RequestHeader("Authorization") String authHeader
	) {
		log.info("UserController/refreshToken");
		try {
			return  ResponseEntity.ok(authService.refreshToken(userService.getUserEmail(userId), authHeader));
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ApiResponse("fail","refreshToken",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "마이페이지 기능", description = "유저의 개인정보 조회를 위한 API \n \n" +
			"<필수입력> \n " +
			"- userId : User table의 Primary Key")
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserInfo(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable("userId") int userId,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.info("UserController/getUserInfo");
		try {
			String token = authHeader.substring(7);
			String userEmail = jwtService.getUserEmailFromToken(token);
			User user = userService.getPublicInfo(userEmail).get();
			Optional<PageResponse<MyChallenge>> challenges = userService.getChallenges(userId, page, size);
			List<UserGoal> goals = userGoalService.getUserGoals(userId);

			if(user != null) {
				user.setChallenges(challenges.get());
				user.setGoals(goals);
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ApiResponse("empty","getUserInfo",204), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<> (new ApiResponse("fail","getUserInfo",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "유저 권한 변경", description = "유저 관리자 설정을 위한 API(관리자만 가능) \n \n" +
			"<필수입력> \n " +
			"- userId : User table의 Primary Key")
	@PatchMapping("/user/{userId}")
	public ResponseEntity<?> updateUserRole(@PathVariable("userId") int userId) {
		log.info("UserController/updateUserRole");
		try {
			Optional<Integer> change = userService.updateUserRole(userId);
			return new ResponseEntity<>(new ApiResponse("success", "updateUserRole", 200), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<> (new ApiResponse("fail","updateUserRole",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "내 정보 변경", description = "개인정보 변경을 위한 API \n \n")
	@PutMapping("/user/{userId}")
	public ResponseEntity<?> updateUserInfo(@PathVariable("userId") @RequestBody User user) {
		log.info("UserController/updateUserInfo");
		try {
			Optional<Integer> change = userService.updateUserInfo(user);
			return new ResponseEntity<> (new ApiResponse("success", "modifyUserInfo", 200), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<> (new ApiResponse("fail","modifyUserInfo",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "참여한 챌린지 목록 반환 기능", description = "유저가 참여한 챌린지 목록 조회를 위한 API \n \n" +
			"<필수입력> \n " +
			"- userId : User table의 Primary Key")
	@GetMapping("/user/{userId}/challenge")
	public ResponseEntity<?> getChallenges(
			@PathVariable("userId") int userId,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.info("UserController/getChallenges");
		try {
			Optional<PageResponse<MyChallenge>> challenges = userService.getChallenges(userId, page, size);
			if(challenges.isPresent()) {
				return new ResponseEntity<> (challenges.get().getContent(), HttpStatus.OK);
			} else {
				return new ResponseEntity<> (new ApiResponse("empty", "getChallenges", 204), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<> (new ApiResponse("fail","getChallenges",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "탈퇴 기능", description = "유저 탈퇴를 위한 API")
	@PutMapping("/user/{userId}/withdraw")
	public ResponseEntity<?> withdraw(@PathVariable("userId") int userId) {
		log.info("UserController/withdraw");
		try {
			Optional<Integer> delete = userService.withdrawUser(userId);
			return new ResponseEntity<> (new ApiResponse("success", "withdraw", 200), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<> (new ApiResponse("fail","withdraw",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
		log.info("UserController/logout");
		String token = authHeader.substring(7);
		authService.logout(token);
		return new ResponseEntity<> (new ApiResponse("success", "logout", 200), HttpStatus.OK);
	}

	@PostMapping("/auth/email")
	public ResponseEntity<?> findEmail(
			@RequestBody User user
	) {
		log.info("UserController/findEmail");
		try {
			Optional<String> email = userService.findEmail(user);
			if (email.isPresent()) {
				return new ResponseEntity<>(email, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ApiResponse("empty", "findEmail", 204), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<> (new ApiResponse("fail","findEmail",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
