package com.wor.dash.challenge.controller;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.ChallengeCategory;
import com.wor.dash.challenge.model.service.ChallengeService;
import com.wor.dash.comment.model.Comment;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.response.ApiResponse;
import com.wor.dash.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Challenge Controller", description = "챌린지 기능을 관리합니다. " +
        "챌린지의 진행여부(challengeIsEnded)는 진행중(0), 종료(1)입니다.")
@CrossOrigin("*")
@Slf4j
public class ChallengeController {

    private final ChallengeService challengeService;
    private List<Comment> commentList;

    @Operation(summary = "챌린지 추가", description = "챌린지 추가를 위한 API \n\n " +
            "<필수입력> \n\n " +
            "### body \n " +
            "- challengeCategoryCode : 챌린지 카테고리 코드 \n " +
            "- challengeTitle : 챌린지 제목 \n " +
            "- challengeDescription : 챌린지 내용 \n" +
            "- challengeAuthorId : 챌린지 작성자 ID (userId, 관리자만 가능) \n" +
            "- challengeTargetCnt : 챌린지 목표 인원 수 \n\n" +
            "- challengeCreateDate : 챌린지 생성날짜 (ex.2024-07-01 00:00:00) \n" +
            "- challengeDeleteDate : 챌린지 종료날짜 (ex.2024-07-07 23:59:59)"
    )
    @PostMapping("/admin/challenge")
    public ResponseEntity<Void> createChallenge(@RequestBody Challenge challenge) {
        challengeService.addChallenge(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "챌린지 상세 조회", description = "챌린지를 상세 조회하기 위한 API \n \n" +
            "<필수입력> \n" +
            "### path \n" +
            "- challengeId : 조회할 챌린지 ID")
    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<?> getChallengeDetail(@PathVariable("challengeId") int challengeId) {
        Challenge challenge = challengeService.getChallengeById(challengeId);

        return ResponseEntity.ok(challenge);
    }

    @Operation(summary = "챌린지 전체 조회", description = "챌린지 진행여부 상관없이 전체 조회하기 위한 API")
    @GetMapping("/challenge")
    public ResponseEntity<?> getAllChallenges(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("ChallengeController/getAllChallenges");
        try {
            PageResponse<Challenge> challenges = challengeService.getAllChallenges(page, size);
            if (challenges.getContent().size() > 0) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "진행중인 챌린지 조회", description = "진행중인 챌린지를 전체 조회하기 위한 API")
    @GetMapping("/challenge/active")
    public ResponseEntity<?> getActiveChallengeList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponse<Challenge> challenges = challengeService.getActiveChallenges(page, size);
            if (challenges.getContent().size() > 0) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "종료된 챌린지 조회", description = "종료된 챌린지를 전체 조회하기 위한 API")
    @GetMapping("/challenge/end")
    public ResponseEntity<?> getEndedChallengeList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponse<Challenge> challenges = challengeService.getEndedChallenges(page, size);
            if (challenges.getContent().size() > 0) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "챌린지 수정", description = "챌린지를 수정하기 위한 API \n\n" +
            " <필수 입력> \n\n" +
            "### path \n" +
            "- challengeId : 수정할 챌린지 ID \n" +
            "### body \n" +
            "- challengeCategoryCode : 챌린지 카테고리 코드 \n " +
            "- challengeTitle : 챌린지 제목 \n " +
            "- challengeDescription : 챌린지 내용 \n" +
            "- challengeTargetCnt : 챌린지 목표 인원 수 \n\n" +
            "- challengeCreateDate : 챌린지 생성날짜 (ex.2024-07-01 00:00:00) \n" +
            "- challengeDeleteDate : 챌린지 종료날짜 (ex.2024-07-07 23:59:59) \n")
    @PutMapping("/admin/challenge/{challengeId}")
    public ResponseEntity<?> updateChallenge(@PathVariable("challengeId") int challengeId, @RequestBody Challenge challenge) {
        boolean isS = challengeService.editChallenge(challengeId, challenge);
        if (isS) {
            return new ResponseEntity<>(new ApiResponse("success", "updateChallenge", 200), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse("fail", "updateChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "챌린지 종료", description = "진행중인 챌린지를 종료하기 위한 API\n\n" +
            "<필수입력> \n\n" +
            "### path  \n" +
            "- challengeId : 종료할 챌린지 ID")
    @DeleteMapping("/admin/challenge/{challengeId}")
    public ResponseEntity<?> deleteChallenge(@PathVariable int challengeId) {
        boolean isS = challengeService.removeChallenge(challengeId);
        if (isS) return new ResponseEntity<>(new ApiResponse("success", "deleteChallenge", 200), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("fail", "deleteChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Operation(summary = "챌린지 신청", description = "진행중인 챌린지를 신청하기 위한 API\n\n" +
            "<필수입력> \n\n" +
            "### path  \n" +
            "- challengeId : 신청할 챌린지 ID \n\n" +
            "### body \n" +
            " - userId : 로그인한 유저 ID")
    @PostMapping("/challenge/{challengeId}")
    public ResponseEntity<?> registerChallenge(@RequestBody User user, @PathVariable("challengeId") int challengeId) {
        log.debug("ChallengeController/registerChallenge");
        boolean isS = challengeService.registerChallenge(user, challengeId);
        if (isS) return new ResponseEntity<>(new ApiResponse("success", "registerChallenge", 200), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("empty", "registerChallenge", 409), HttpStatus.CONFLICT);
    }

    @Operation(summary = "챌린지 신청 취소", description = "신청한 챌린지를 취소하기 위한 API\n\n" +
            "<필수입력> \n\n" +
            "### path  \n" +
            "- challengeId : 신청한 챌린지 ID \n\n" +
            "### body \n" +
            " - userId : 로그인한 유저 ID")
    @DeleteMapping("/challenge/{challengeId}")
    public ResponseEntity<?> cancelChallenge(@RequestBody User user, @PathVariable("challengeId") int challengeId) {
        boolean isS = challengeService.cancelChallenge(user, challengeId);
        if (isS) return new ResponseEntity<>(new ApiResponse("success", "cancelChallenge", 200), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("fail", "cancelChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "챌린지 스케쥴 전체 조회", description = "챌린지 스케쥴의 진행여부 상관없이 전체 조회하기 위한 API")
    @GetMapping("/admin/challenge/schedule")
    public ResponseEntity<List<Challenge>> getAllChallengeScheduleList() {
        List<Challenge> challengeList = null;
        try {
            challengeList = challengeService.getAllChallengeScheduleList();
            if (challengeList.size() > 0) {
                return new ResponseEntity<>(challengeList, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "반복중인 챌린지 스케쥴 조회", description = "반복중인 챌린지를 조회하기 위한 API")
    @GetMapping("/admin/challenge/schedule/active")
    public ResponseEntity<List<Challenge>> getActiveChallengeScheduleList() {
        List<Challenge> challengeList = null;
        try {
            challengeList = challengeService.getActiveChallengeScheduleList();
            if (challengeList.size() > 0) {
                return new ResponseEntity<>(challengeList, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "반복종료된 챌린지 스케쥴 조회", description = "반복종료된 챌린지를 조회하기 위한 API")
    @GetMapping("/admin/challenge/schedule/end")
    public ResponseEntity<List<Challenge>> getEndedChallengeScheduleList() {
        List<Challenge> challengeList = null;
        try {
            challengeList = challengeService.getEndedChallengeScheduleList();
            if (challengeList.size() > 0) {
                return new ResponseEntity<>(challengeList, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "챌린지 스케쥴 추가", description = "반복할 챌린지 추가를 위한 API \n\n " +
            "<필수입력> \n\n " +
            "### body \n " +
            "- challengeCategoryCode : 챌린지 카테고리 코드 \n " +
            "- challengeTitle : 챌린지 제목 \n " +
            "- challengeDescription : 챌린지 내용 \n" +
            "- challengeAuthorId : 챌린지 작성자 ID (userId, 관리자만 가능) \n" +
            "- challengeTargetCnt : 챌린지 목표 인원 수 \n\n" +
            "- challengeCreateDate : 챌린지 생성날짜 (ex.2024-07-01 00:00:00) \n" +
            "- challengeDeleteDate : 챌린지 종료날짜 (ex.2024-07-07 23:59:59) \n" +
            "- challengeSchedulerCycle : 챌린지 사이클 설정 (1: 일일 / 2 : 일주일 / 3 : 한달)"
    )
    @PostMapping("/admin/challenge/schedule")
    public ResponseEntity<?> createScheduleChallenge(@RequestBody Challenge challenge) {
        challengeService.addChallengeSchedule(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "챌린지 스케쥴 수정", description = "반복할 챌린지 변경을 위한 API \n\n " +
            "<필수입력> \n\n " +
            "### path \n" +
            "- challengeId : 수정 할 챌린지 ID \n" +
            "### body \n " +
            "- challengeCategoryCode : 챌린지 카테고리 코드 \n " +
            "- challengeTitle : 챌린지 제목 \n " +
            "- challengeDescription : 챌린지 내용 \n" +
            "- challengeTargetCnt : 챌린지 목표 인원 수 \n" +
            "- challengeCreateDate : 챌린지 생성날짜 (ex.2024-07-01 00:00:00) \n" +
            "- challengeDeleteDate : 챌린지 종료날짜 (ex.2024-07-07 23:59:59) \n" +
            "- challengeSchedulerCycle : 챌린지 사이클 설정 (1: 일일 / 2 : 일주일 / 3 : 한달)"
    )
    @PutMapping("/admin/challenge/schedule/{challengeId}")
    public ResponseEntity<?> updateScheduleChallenge(@PathVariable("challengeId") int challengeId, @RequestBody Challenge challenge) {
        boolean isS = challengeService.editChallengeSchedule(challengeId, challenge);
        if (isS) {
            return new ResponseEntity<>(new ApiResponse("success", "updateScheduleChallenge", 200), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse("fail", "updateScheduleChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "챌린지 스케쥴 종료", description = "반복할 챌린지 종료를 위한 API \n\n " +
            "<필수입력> \n\n " +
            "### path \n" +
            "- challengeId : 종료 할 챌린지 ID"
    )
    @DeleteMapping("/admin/challenge/schedule/{challengeId}")
    public ResponseEntity<?> deleteScheduleChallenge(@PathVariable("challengeId") int challengeId) {
        System.out.println("challengeId: " + challengeId);
        boolean isS = challengeService.deleteChallengeSchedule(challengeId);
        if (isS) {
            return new ResponseEntity<>(new ApiResponse("success", "deleteScheduleChallenge", 200), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse("fail", "deleteScheduleChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "챌린지 카테고리 반환", description = "챌린지 카테고리를 가져오기 위한 API \n\n ")
    @GetMapping("/challenge/category")
    public ResponseEntity<?> getChallengeCategory() {
        Optional<List<ChallengeCategory>> list = challengeService.getChallengeCategories();
        try {
            if (list.isPresent()) {
                return new ResponseEntity<>(list.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("empty", "getChallengeCategory", 204), HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "getChallengeCategory", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "챌린지 스케쥴 종료", description = "반복할 챌린지 종료를 위한 API \n\n " +
            "<필수입력> \n\n " +
            "- challengeId : 이름은 challengeId지만 스케쥴러의 ID")
    @GetMapping("/admin/challenge/schedule/{challengeId}")
    public ResponseEntity<?> getChallengeSchedule(@PathVariable("challengeId") int challengeId) {
        Optional<Challenge> schedule = challengeService.getChallengeSchedule(challengeId);
        try {
            if (schedule.isPresent()) {
                return new ResponseEntity<>(schedule.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("empty", "getChallengeSchedule", 204), HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail", "getChallengeSchedule", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}