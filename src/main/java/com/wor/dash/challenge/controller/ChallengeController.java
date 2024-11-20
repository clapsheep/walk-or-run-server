package com.wor.dash.challenge.controller;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.service.ChallengeService;
import com.wor.dash.comment.model.Comment;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/challenge")
@Tag(name = "Challenge Controller", description = "챌린지 기능을 관리합니다. " +
        "챌린지의 진행여부(challengeIsEnded)는 진행중(0), 종료(1)입니다.")
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
            "- challengeCreateDate(Optional) : 챌린지 생성날짜 (미입력시 현재 시간으로 자동 설정) \n" +
            "- challengeDeleteDate(Optional) : 챌린지 종료날짜 (미입력시 null)"
    )
    @PostMapping
    public ResponseEntity<Void> createChallenge(@RequestBody Challenge challenge) {
        challengeService.addChallenge(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "챌린지 상세 조회", description = "챌린지를 상세 조회하기 위한 API \n \n" +
            "<필수입력> \n" +
            "### path \n" +
            "- challengeId : 조회할 챌린지 ID")
    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getChallengeDetail(@PathVariable("challengeId") int challengeId) {
        Challenge challenge = challengeService.getChallengeById(challengeId);

        return ResponseEntity.ok(challenge);
    }

    @Operation(summary = "챌린지 전체 조회", description = "챌린지 진행여부 상관없이 전체 조회하기 위한 API")
    @GetMapping()
    public ResponseEntity<?> getAllChallenges(int page, int size) {
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
    @GetMapping("/active")
    public ResponseEntity<?> getActiveChallengeList(int page, int size) {
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
    @GetMapping("end")
    public ResponseEntity<?> getEndedChallengeList(int page, int size) {
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
            " <필수 입력> \n" +
            "### path \n" +
            "- challengeId : 수정할 챌린지 ID \n" +
            "### body \n" +
            "- challengeCategoryCode : 챌린지 카테고리 코드 \n " +
            "- challengeTitle : 챌린지 제목 \n " +
            "- challengeDescription : 챌린지 내용 \n" +
            "- challengeTargetCnt : 챌린지 목표 인원 수 \n\n" +
            "- challengeCreateDate(Optional) : 챌린지 생성날짜 (미입력시 기존 시간 유지) \n" +
            "- challengeDeleteDate(Optional) : 챌린지 종료날짜 (미입력시 기존 시간 유지) \n" +
            "- challengeIsEnded(Optional) : 챌린지 종료 여부(0 : 진행중 / 1 : 종료)")
    @PutMapping("/{challengeId}")
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
            "- challengeId : 종료할 Challenge ID")
    @DeleteMapping("/{challengeId}")
    public ResponseEntity<?> deleteChallenge(@PathVariable int challengeId) {
        boolean isS = challengeService.removeChallenge(challengeId);
        if (isS) return new ResponseEntity<>(new ApiResponse("success", "deleteChallenge", 200), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("fail", "deleteChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
