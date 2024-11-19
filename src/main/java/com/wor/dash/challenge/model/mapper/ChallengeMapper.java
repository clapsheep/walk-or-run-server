package com.wor.dash.challenge.model.mapper;

import com.wor.dash.challenge.model.Challenge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChallengeMapper {
    void insertChallenge(Challenge challenge);

    List<Challenge> selectChallengeList();

    List<Challenge> selectActiveChallengeList();

    List<Challenge> selectEndedChallengeList();

    Challenge selectChallenge(int challengeId);

    int updateChallenge(Challenge challenge);

    int deleteChallenge(int challengeId);
}
