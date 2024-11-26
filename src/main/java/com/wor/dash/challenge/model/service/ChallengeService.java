package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.ChallengeCategory;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.user.model.User;

import java.util.List;
import java.util.Optional;

public interface ChallengeService {
    void addChallenge(Challenge challenge);

    PageResponse<Challenge> getAllChallenges(int currentPage, int pageSize);

    PageResponse<Challenge> getActiveChallenges(int currentPage, int pageSize);

    PageResponse<Challenge> getEndedChallenges(int currentPage, int pageSize);

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

    Optional<List<ChallengeCategory>> getChallengeCategories();

    Optional<Challenge> getChallengeSchedule(int challengeId);
}