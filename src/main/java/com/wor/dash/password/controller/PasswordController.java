package com.wor.dash.password.controller;

import com.wor.dash.password.model.PasswordAnswer;
import com.wor.dash.password.model.PasswordFindQnA;
import com.wor.dash.password.model.PasswordQuestion;
import com.wor.dash.password.model.service.PasswordService;
import com.wor.dash.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/password")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

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
