package com.wor.dash.challenge.model.controller;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.service.ChallengeService;
import com.wor.dash.comment.model.Comment;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private List<Comment> commentList;

    @Operation(summary = "챌린지 추가", description = "챌린지 추가를 위한 API \n \n" +
            "<필수입력> \n " +
            "- challengeCategoryCode : 챌린지 카테고리 코드 \n " +
            "- challengeTitle : 챌린지 제목 \n " +
            "- challengeDescription : 챌린지 내용 \n" +
            "- challengeAuthorId : 챌린지 작성자 id (userId, 관리자만 가능)")
    @PostMapping
    public ResponseEntity<Void> createChallenge(@RequestBody Challenge challenge) {
        challengeService.addChallenge(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "챌린지 상세 조회", description = "챌린지를 상세 조회하기 위한 API \n \n" +
            "<필수입력> \n" +
            "- challengeId : 조회할 챌린지 id \n")
    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getChallengeDetail(@PathVariable("challengeId") int challengeId) {
        Challenge challenge = challengeService.getChallengeById(challengeId);

        return ResponseEntity.ok(challenge);
    }

    @Operation(summary = "챌린지 전체 조회", description = "챌린지 진행여부 상관없이 전체 조회하기 위한 API")
    @GetMapping()
    public ResponseEntity<List<Challenge>> getAllChallenges() {
        List<Challenge> challenges = null;
        try {
            challenges = challengeService.getAllChallenges();
            if (challenges.size() > 0) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "챌린지 전체 조회(진행중)", description = "진행중인 챌린지를 전체 조회하기 위한 API")
    @GetMapping("/active")
    public ResponseEntity<List<Challenge>> getActiveChallengeList() {
        List<Challenge> challenges = null;
        try {
            challenges = challengeService.getActiveChallenges();
            if (challenges.size() > 0) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "챌린지 전체 조회(종료)", description = "종료된 챌린지를 전체 조회하기 위한 API")
    @GetMapping("end")
    public ResponseEntity<List<Challenge>> getEndedChallengeList() {
        List<Challenge> challenges = null;
        try {
            challenges = challengeService.getEndedChallenges();
            if (challenges.size() > 0) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "챌린지 수정", description = "챌린지를 수정하기 위한 API \n\n" +
            " <필수 입력> \n" +
            "- challengeTitle : 챌린지 제목 \n" +
            "- challengeDescription : 챌린지 내용 \n" +
            "- challengeAuthorId : 챌린지 작성자(관리자) \n" +
            "- challengeTargetId : 챌린지 목표 인원 수")
    @PutMapping
    public ResponseEntity<?> updateChallenge(@RequestBody Challenge challenge) {
        boolean isS = challengeService.editChallenge(challenge);
        if (isS) {
            return new ResponseEntity<>(new ApiResponse("success", "updateChallenge", 200), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse("fail", "updateChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "챌린지 종료", description = "진행중인 챌린지를 종료하기 위한 API")
    @DeleteMapping("/{challengeId}")
    public ResponseEntity<?> deleteChallenge(@PathVariable int challengeId) {
        boolean isS = challengeService.removeChallenge(challengeId);
        if (isS) return new ResponseEntity<>(new ApiResponse("success", "deleteChallenge", 200), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("fail", "deleteChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
