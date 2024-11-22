package com.wor.dash.password.controller;

import com.wor.dash.jwt.model.service.AuthenticationService;
import com.wor.dash.password.PasswordChangeUtil;
import com.wor.dash.password.model.PasswordAnswer;
import com.wor.dash.password.model.PasswordQuestion;
import com.wor.dash.password.model.service.PasswordService;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.response.AuthenticationResponse;
import com.wor.dash.user.model.User;
import com.wor.dash.user.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
@AllArgsConstructor
@Slf4j
@Tag(name = "Password Controller", description = "비밀번호 찾기 기능을 관리합니다.")
public class PasswordController {

    private final PasswordService passwordService;
    private final AuthenticationService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "비밀번호 확인질문 리스트", description = "비밀번호 확인질문 리스트 조회를 위한 API")
    @GetMapping("/auth/password-question")
    public ResponseEntity<?> getQuestionList() {
        log.info("PasswordController/getQuestionList");
        Optional<Map<Integer, String>> list = passwordService.allQuestions();
        try {
            if(list.isPresent()) {
                return new ResponseEntity<>(list.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "getQuestionList", 500) ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "비밀번호 찾기", description = "비밀번호 모를 때 유저가 있는지 확인하기 위한 API \n \n" +
            "<필수입력> \n " +
            "- userEmail : 유저 이메일 \n" +
            "- userPasswordQuestionId : 유저 비밀번호 확인 질문의 id \n" +
            "- userPasswordAnswer : 유저 비밀번호 확인 질문 답변 \n")
    @PostMapping("/auth/password/find")
    public ResponseEntity<?> findPassword(
            @RequestBody User user
    ) {
        log.info("PasswordController/findPassword");
        try {
            Optional<Integer> isUser = passwordService.findPassword(user);
            if (isUser.isPresent()) {
                return new ResponseEntity<>(new ApiResponse("success", "findPassword", 200), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse("empty", "findPassword", 204), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "findPassword", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경을 위한 API \n \n" +
            "<필수입력> \n " +
            "- userEmail : 유저 이메일" +
            "- userPassword: 새 비밀번호")
    @PostMapping("/user/password/change")
    public ResponseEntity<?> changePassword(@RequestBody User user) {
        log.info("PasswordController/changePassword");
        try {
            User changeUser = userService.getUserImportantInfo(user.getUserEmail()).get();
            changeUser.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            Optional<Integer> change = userService.updateUserPassword(changeUser);
            if(change.isPresent()) {
                return new ResponseEntity<>(new ApiResponse("success", "changePassword", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("empty", "changePassword", 204), HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "changePassword", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "마이페이지에서 비밀번호 변경", description = "마이페이지에서 비밀번호 변경을 위한 API \n \n" +
            "<필수입력> \n " +
            "- header(Authorization) : 현재 로그인된 유저의 access token" +
            "- userid : 유저 아이디(pk)" +
            "- userPassword : 유저 현재 패스워드" +
            "- newPassword : 바꿀 패스워드" +
            "- userPasswordAnswer : 유저 비밀번호 확인질문 답변")
    @PostMapping("/user/{userId}/password/change")
    public ResponseEntity<?> changePw(
            HttpServletRequest request,
            @RequestBody PasswordChangeUtil passwordChangeUtil) {
        log.info("PasswordController/changePw");

        if(!userService.checkUserPassword(passwordChangeUtil)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("Current password is incorrect", "changePw", 400), HttpStatus.BAD_REQUEST);
        }

        String authHeader = request.getHeader("Authorization");

        String email = userService.getUserEmail(passwordChangeUtil.getUserId());
        User user = userService.getUserImportantInfo(email).get();
        user.setUserPassword(passwordEncoder.encode(passwordChangeUtil.getNewPassword()));
        userService.updateUserPassword(user);
        User nUser = userService.getUserImportantInfo(user.getUserEmail()).get();
        PasswordAnswer answer = new PasswordAnswer();
        answer.setUserId(nUser.getUserId());
        answer.setQuestionId(user.getUserPasswordQuestionId());
        answer.setPasswordQuestionAnswer(user.getUserPasswordAnswer());
        passwordService.updateAnswer(answer);

        AuthenticationResponse authenticationResponse = authService.refreshToken(user.getUserEmail(), authHeader);

        return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(authenticationResponse.getAccessToken(), authenticationResponse.getRefreshToken(), "success"), HttpStatus.OK);
    }
}
