package com.wor.dash.challenge.model.controller;

import java.util.List;

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
@RequestMapping("api/wor/challenges")
public class ChallengeController {
	
	private final ChallengeService challengeService;
	
	
	@PostMapping
    public ResponseEntity<Void> createChallenge(@RequestBody Challenge challenge) {
        challengeService.addChallenge(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
	
	@GetMapping("/{challengeId}")
	public ResponseEntity<Challenge> getChallengeDetail(@PathVariable("challengeId") int challengeId) {
        Challenge challenge = challengeService.getChallengeById(challengeId);
        return ResponseEntity.ok(challenge);
    }
	
	@GetMapping
    public ResponseEntity<List<Challenge>> getChallengeList() {
        List<Challenge> challenges = challengeService.getAllChallenges();
        return ResponseEntity.ok(challenges);
    }
	
	@PutMapping
    public ResponseEntity<Void> updateChallenge(@RequestBody Challenge challenge) {
        challengeService.editChallenge(challenge);
        return ResponseEntity.ok().build();
    }
	
	@DeleteMapping("/{challengeId}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable int challengeId) {
        challengeService.removeChallenge(challengeId);
        return ResponseEntity.ok().build();
    }
}
