package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;

import java.util.List;

public interface ChallengeService {
    void addChallenge(Challenge challenge);

    List<Challenge> getAllChallenges();

    List<Challenge> getActiveChallenges();

    List<Challenge> getEndedChallenges();

    Challenge getChallengeById(int challengeId);

    boolean editChallenge(Challenge challenge);

    boolean removeChallenge(int challengeId);

    void addDailyChallenge(int challenge_scheduler_cycle);

    void addWeeklyChallenge(int challenge_scheduler_cycle);

    void addMonthlyChallenge(int challenge_scheduler_cycle);

    void checkIsEndedChallenge();
}
