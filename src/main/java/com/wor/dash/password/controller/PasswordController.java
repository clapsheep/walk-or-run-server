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
        Optional<List<PasswordQuestion>> list = passwordService.allQuestions();
        try {
            if(list.isPresent()) {
                return new ResponseEntity<>(list.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "getQuestionList", 500) ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/password/change")
    public ResponseEntity<?> changePw(
            HttpServletRequest request,
            @RequestBody PasswordChangeUtil passwordChangeUtil) {
        log.debug("AuthenticationController/checkPw");
        log.debug(passwordChangeUtil.toString() + "--------------------------------1");

        if(!userService.checkUserPassword(passwordChangeUtil)) {
            System.out.println("비밀번호 틀림");
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

//    @Operation(summary = "특정 비밀번호 확인질문 조회", description = "특정 비밀번호 확인질문 조회를 위한 API \n \n" +
//            "<필수입력> \n " +
//            "- qudstionId : 비밀번호 질문 아이디")
//    @GetMapping("/question/{questionId}")
//    public ResponseEntity<?> getQuestion(@PathVariable int questionId) {
//        Optional<PasswordQuestion> question = passwordService.getQuestion(questionId);
//        try {
//            if(question.isPresent()) {
//                return new ResponseEntity<>(question.get(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch(Exception e) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "getQuestion", 500) ,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @Operation(summary = "답변 조회", description = "작성한 답변 조회를 위한 API \n \n" +
//            "<필수입력> \n " +
//            "- userId : 현재 로그인된 유저의 아이디")
//    @GetMapping("/answer/{userId}")
//    public ResponseEntity<?> getAnswer(@PathVariable int userId) {
//        Optional<PasswordFindQnA> qna = passwordService.getQnA(userId);
//        try {
//            if(qna.isPresent()) {
//                return new ResponseEntity<>(qna.get(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch(Exception e) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "getAnswer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @Operation(summary = "비밀번호 확인 질문 및 답변 추가", description = "비밀번호 확인 질문 및 답변 추가를 위한 API \n \n" +
//            "<필수입력> \n " +
//            "- passwordAnswerId : 비밀번호 답변 아이디 \n " +
//            "- userId : 현재 로그인된 유저의 아이디 \n " +
//            "- qudstionId : 비밀번호 질문 아이디 \n " +
//            "- passwordQuestionAnswer : 비밀번호 질문 답변")
//    @PostMapping
//    public ResponseEntity<?> addAnswer(@RequestBody PasswordAnswer answer) {
//        Optional<Integer> insert = passwordService.addAnswer(answer);
//        try {
//            if (insert.isPresent()) {
//                return new ResponseEntity<ApiResponse>(new ApiResponse("success", "addAnswer", 200), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<ApiResponse>(new ApiResponse("nocontent", "addAnswer", 204), HttpStatus.NOT_FOUND);
//            }
//        } catch(Exception e) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "addAnswer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @Operation(summary = "비밀번호 확인 질문 및 답변 추가", description = "비밀번호 확인 질문 및 답변 추가를 위한 API \n \n" +
//            "<필수입력> \n " +
//            "- userId : 현재 로그인된 유저의 아이디")
//    @PutMapping("/{userId}")
//    public ResponseEntity<?> updateAnswer(@PathVariable("userId") int userId, @RequestBody PasswordAnswer answer) {
//        Optional<Integer> update = passwordService.updateAnswer(answer);
//        try {
//            if (update.isPresent()) {
//                return new ResponseEntity<ApiResponse>(new ApiResponse("success", "updateAnswer", 200), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<ApiResponse>(new ApiResponse("nocontent", "updateAnswer", 204), HttpStatus.NOT_FOUND);
//            }
//        } catch(Exception e) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "updateAnswer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
