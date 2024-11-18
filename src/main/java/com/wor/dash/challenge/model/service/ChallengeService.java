package com.wor.dash.challenge.model.service;

import java.util.List;

import com.wor.dash.challenge.model.Challenge;

public interface ChallengeService {
	void addChallenge(Challenge challenge);		
	List<Challenge> getAllChallenges();			
	Challenge getChallengeById(int challengeId); 	
	boolean editChallenge(Challenge challenge);	
	boolean removeChallenge(int challengeId);	
}
