package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.user.model.User;

import java.util.List;

public interface ChallengeService {
    void addChallenge(Challenge challenge);

    List<Challenge> getAllChallengeList();

    List<Challenge> getActiveChallengeList();

    List<Challenge> getEndedChallengeList();

    Challenge getChallengeById(int challengeId);

    boolean editChallenge(int challengeId, Challenge challenge);

    boolean removeChallenge(int challengeId);

    void addDailyChallenge(int challenge_scheduler_cycle);

    void addWeeklyChallenge(int challenge_scheduler_cycle);

    void addMonthlyChallenge(int challenge_scheduler_cycle);

    void checkIsEndedChallenge();

    boolean registerChallenge(User user, int challengeId);

    boolean cancelChallenge(User user, int challengeId);

    void addChallengeSchedule(Challenge challenge);

    boolean editChallengeSchedule(int challengeId, Challenge challenge);

    boolean deleteChallengeSchedule(int challengeId);

    List<Challenge> getAllChallengeScheduleList();

    List<Challenge> getActiveChallengeScheduleList();

    List<Challenge> getEndedChallengeScheduleList();
}
