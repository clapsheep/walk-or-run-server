package com.wor.dash.password.controller;

import com.wor.dash.password.model.PasswordAnswer;
import com.wor.dash.password.model.PasswordFindQnA;
import com.wor.dash.password.model.PasswordQuestion;
import com.wor.dash.password.model.service.PasswordService;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/password")
@AllArgsConstructor
@Tag(name = "Password Controller", description = "비밀번호 찾기 기능을 관리합니다.")
public class PasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "비밀번호 확인질문 리스트", description = "비밀번호 확인질문 리스트 조회를 위한 API")
    @GetMapping("")
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

    @Operation(summary = "특정 비밀번호 확인질문 조회", description = "특정 비밀번호 확인질문 조회를 위한 API \n \n" +
            "<필수입력> \n " +
            "- qudstionId : 비밀번호 질문 아이디")
    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable int questionId) {
        Optional<PasswordQuestion> question = passwordService.getQuestion(questionId);
        try {
            if(question.isPresent()) {
                return new ResponseEntity<>(question.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "getQuestion", 500) ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "답변 조회", description = "작성한 답변 조회를 위한 API \n \n" +
            "<필수입력> \n " +
            "- userId : 현재 로그인된 유저의 아이디")
    @GetMapping("/answer/{userId}")
    public ResponseEntity<?> getAnswer(@PathVariable int userId) {
        Optional<PasswordFindQnA> qna = passwordService.getQnA(userId);
        try {
            if(qna.isPresent()) {
                return new ResponseEntity<>(qna.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "getAnswer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "비밀번호 확인 질문 및 답변 추가", description = "비밀번호 확인 질문 및 답변 추가를 위한 API \n \n" +
            "<필수입력> \n " +
            "- passwordAnswerId : 비밀번호 답변 아이디 \n " +
            "- userId : 현재 로그인된 유저의 아이디 \n " +
            "- qudstionId : 비밀번호 질문 아이디 \n " +
            "- passwordQuestionAnswer : 비밀번호 질문 답변")
    @PostMapping("/isnert")
    public ResponseEntity<?> addAnswer(@RequestBody PasswordAnswer answer) {
        Optional<Integer> insert = passwordService.addAnswer(answer);
        try {
            if (insert.isPresent()) {
                return new ResponseEntity<ApiResponse>(new ApiResponse("success", "addAnswer", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse("nocontent", "addAnswer", 204), HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "addAnswer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "비밀번호 확인 질문 및 답변 추가", description = "비밀번호 확인 질문 및 답변 추가를 위한 API \n \n" +
            "<필수입력> \n " +
            "- userId : 현재 로그인된 유저의 아이디")
    @PutMapping("/update")
    public ResponseEntity<?> updateAnswer(@RequestBody PasswordAnswer answer) {
        Optional<Integer> update = passwordService.updateAnswer(answer);
        try {
            if (update.isPresent()) {
                return new ResponseEntity<ApiResponse>(new ApiResponse("success", "updateAnswer", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse("nocontent", "updateAnswer", 204), HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("fail", "updateAnswer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
