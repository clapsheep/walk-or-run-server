package com.wor.dash.challenge.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.mapper.ChallengeMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

	 private final ChallengeMapper challengeMapper;
	
	
	@Override
	public void addChallenge(Challenge challenge) {
		challengeMapper.insertChallenge(challenge);
		
	}

	@Override
	public List<Challenge> getAllChallenges() {
		return challengeMapper.selectChallengeList();
	}

	@Override
	public Challenge getChallengeById(int challengeId) {
		
		return challengeMapper.selectChallenge(challengeId);
	}

	@Override
	public boolean editChallenge(Challenge challenge) {
		int result = challengeMapper.updateChallenge(challenge);
		
		return result == 1;
	}

	@Override
	public boolean removeChallenge(int challengeId) {
		int result = challengeMapper.deleteChallenge(challengeId);
		
		return result == 1;
	}
}
