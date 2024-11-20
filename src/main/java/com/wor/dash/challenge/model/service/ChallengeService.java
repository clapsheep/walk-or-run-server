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

    void addDailyChallenge();

    void addWeeklyChallenge();

    void addMonthlyChallenge();

    void checkIsEndedChallenge();
}
