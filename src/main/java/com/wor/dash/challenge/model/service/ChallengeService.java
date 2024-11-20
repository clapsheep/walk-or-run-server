package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChallengeService {
    void addChallenge(Challenge challenge);

    List<Challenge> getAllChallengeList();

    List<Challenge> getActiveChallengeList();

    List<Challenge> getEndedChallengeList();

    Challenge getChallengeById(int challengeId);

    boolean editChallenge(@Param("challengeId") int challengeId, @Param("challenge") Challenge challenge);

    boolean removeChallenge(int challengeId);

    void addDailyChallenge(int challenge_scheduler_cycle);

    void addWeeklyChallenge(int challenge_scheduler_cycle);

    void addMonthlyChallenge(int challenge_scheduler_cycle);

    void checkIsEndedChallenge();
}
