package com.wor.dash.challenge.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wor.dash.challenge.model.Challenge;

@Mapper
public interface ChallengeMapper {
	void insertChallenge(Challenge challenge);
	List<Challenge> selectChallengeList();
	Challenge selectChallenge(int challengeId);
	int updateChallenge(Challenge challenge);
	int deleteChallenge(int challengeId);
}
