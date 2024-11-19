package com.wor.dash.challenge.controller;

import java.util.List;

import com.wor.dash.comment.model.Comment;
import com.wor.dash.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.service.ChallengeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private List<Comment> commentList;

    @PostMapping
    public ResponseEntity<Void> createChallenge(@RequestBody Challenge challenge) {
        challengeService.addChallenge(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getChallengeDetail(@PathVariable("challengeId") int challengeId) {
        Challenge challenge = challengeService.getChallengeById(challengeId);

        return ResponseEntity.ok(challenge);
    }

    @GetMapping
    public ResponseEntity<List<Challenge>> getChallengeList() {
        System.out.println("들어왔어요");
        List<Challenge> challenges = null;
        System.out.println("여기등장1");
        try {
            challenges = challengeService.getAllChallenges();
            if (challenges.size() > 0) {
                System.out.println("여기등장2");
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateChallenge(@RequestBody Challenge challenge) {
        boolean isS = challengeService.editChallenge(challenge);
        if(isS) {
            return new ResponseEntity<>(new ApiResponse("success", "updateChallenge", 200), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse("fail", "updateChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{challengeId}")
    public ResponseEntity<?> deleteChallenge(@PathVariable int challengeId) {
        boolean isS = challengeService.removeChallenge(challengeId);
        if(isS) return  new ResponseEntity<>(new ApiResponse("success", "deleteChallenge", 200), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("fail", "deleteChallenge", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
