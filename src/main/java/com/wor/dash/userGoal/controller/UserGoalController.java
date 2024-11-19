package com.wor.dash.userGoal.controller;

import com.wor.dash.response.ApiResponse;
import com.wor.dash.userGoal.model.UserGoal;
import com.wor.dash.userGoal.model.service.UserGoalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/user/goal")
public class UserGoalController {
    private final UserGoalService userGoalService;

    @GetMapping
    public ResponseEntity<?> getUserGoalList(@RequestBody int userId) {
        try {
            List<UserGoal> userGoals = userGoalService.getUserGoals(userId);
            if (userGoals.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("empty", "getUserGoalList", 204), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(userGoals, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "getUserGoalList", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{goalId}")
    public ResponseEntity<?> getUserGoalById(@PathVariable int goalId) {
        try {
            UserGoal userGoal = userGoalService.getUserGoalById(goalId);
            return new ResponseEntity<>(userGoal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "getUserGoalById", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<?> createUserGoal(@RequestBody UserGoal userGoal) {
        try {
            userGoalService.addUserGoal(userGoal);
            return new ResponseEntity<>(new ApiResponse("success", "createUserGoal", 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", "createUserGoal", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUserGoal(@RequestBody UserGoal userGoal) {
        try {
            userGoalService.updateUserGoal(userGoal);
            return new ResponseEntity<>(new ApiResponse("success", "updateUserGoal", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "updateUserGoal", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserGoal(@RequestBody int userGoalId) {
        try {
            userGoalService.deleteUserGoal(userGoalId);
            return new ResponseEntity<>(new ApiResponse("success", "deleteUserGoal", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "deleteUserGoal", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
