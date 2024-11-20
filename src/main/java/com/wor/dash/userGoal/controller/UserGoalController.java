package com.wor.dash.userGoal.controller;

import com.wor.dash.response.ApiResponse;
import com.wor.dash.userGoal.model.UserGoal;
import com.wor.dash.userGoal.model.service.UserGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "UserGoal Controller", description = "유저의 달리기, 걷기 목표를 관리합니다. 운동 기록 중 분당 걸음 수(step_count/시간)가 130 이상이면 달리기(1), 미만이면 걷기(2)로 판단합니다.")
@RequestMapping("/api/user")
public class UserGoalController {
    private final UserGoalService userGoalService;


    @Operation(summary = "로그인한 유저의 목표 리스트 가져오기", description = "목표 목록 API  \n\n <필수입력> \n - userId : 로그인한 유저ID")
    @GetMapping("{userId}/goal")
    public ResponseEntity<?> getUserGoalList(@PathVariable int userId) {
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

    @Operation(summary = "특정 목표의 상세정보 가져오기", description = "목표 디테일 API  \n\n <필수입력> \n - goalId : 확인할 목표의 ID")
    @GetMapping("goal/{goalId}")
    public ResponseEntity<?> getUserGoalById(@PathVariable int goalId) {
        try {
            UserGoal userGoal = userGoalService.getUserGoalById(goalId);
            return new ResponseEntity<>(userGoal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "getUserGoalById", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "특정 목표의 상세정보 가져오기", description = "목표 디테일 API  \n\n <필수입력> \n - userId :  로그인 유저 ID \n - challengeCategoryCode :  카테고리(달리기 = 1, 걷기 = 2) \n - challengeCategoryUnitCode :  단위(시간(h) = 1, 거리(km) = 2) \n - startDate :  목표 시작일(ex. 2024-07-01) \n - endDate :  목표 종료일(ex. 2024-07-08) \n - targetAmount : 설정 기간동안 단위 목표량")
    @PostMapping("/goal")
    public ResponseEntity<?> createUserGoal(@RequestBody UserGoal userGoal) {
        try {
            userGoalService.addUserGoal(userGoal);
            return new ResponseEntity<>(new ApiResponse("success", "createUserGoal", 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", "createUserGoal", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "특정 목표의 상세정보 수정하기", description = "목표 디테일 API  \n\n <필수입력> \n - userGoalId :  수정할 목표 ID \n - challengeCategoryCode :  카테고리(달리기 = 1, 걷기 = 2) \n - challengeCategoryUnitCode :  단위(시간(h) = 1, 거리(km) = 2) \n - startDate :  목표 시작일(ex. 2024-07-01) \n - endDate :  목표 종료일(ex. 2024-07-08) \n - targetAmount : 설정 기간동안 단위 목표량")
    @PutMapping("/goal")
    public ResponseEntity<?> updateUserGoal(@RequestBody UserGoal userGoal) {
        try {
            userGoalService.editUserGoal(userGoal);
            return new ResponseEntity<>(new ApiResponse("success", "updateUserGoal", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "updateUserGoal", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // id 하나만 받아서 삭제하는게 맞는가? 그러면 무슨 형태로 받아야하는가?
    @Operation(summary = "목표 삭제하기", description = "목표 디테일 API  \n\n <필수입력> \n - userGoalId :  삭제할 목표 ID")
    @DeleteMapping("/goal/{userGoalId}")
    public ResponseEntity<?> deleteUserGoal(@PathVariable int userGoalId) {
        try {
            userGoalService.removeUserGoal(userGoalId);
            return new ResponseEntity<>(new ApiResponse("success", "deleteUserGoal", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "deleteUserGoal", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
