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
import java.util.Optional;

@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
@Slf4j
@Tag(name = "Password Controller", description = "비밀번호 찾기 기능을 관리합니다.")
public class PasswordController {

    private final PasswordService passwordService;
    private final AuthenticationService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "비밀번호 확인질문 리스트", description = "비밀번호 확인질문 리스트 조회를 위한 API")
    @GetMapping("/password")
    public ResponseEntity<?> getQuestionList() {
        log.info("PasswordController/getQuestionList");
        Optional<List<PasswordQuestion>> list = passwordService.allQuestions();
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

    @PostMapping("/{userId}/password/change")
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
