package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.pageInfo.model.PageResponse;
import org.apache.ibatis.annotations.Param;

public interface ChallengeService {
    void addChallenge(Challenge challenge);

    PageResponse<Challenge> getAllChallenges(int currentPage, int pageSize);

    PageResponse<Challenge> getActiveChallenges(int currentPage, int pageSize);

    PageResponse<Challenge> getEndedChallenges(int currentPage, int pageSize);

    Challenge getChallengeById(int challengeId);

    boolean editChallenge(@Param("challengeId") int challengeId, @Param("challenge") Challenge challenge);

    boolean removeChallenge(int challengeId);

    void addDailyChallenge(int challenge_scheduler_cycle);

    void addWeeklyChallenge(int challenge_scheduler_cycle);

    void addMonthlyChallenge(int challenge_scheduler_cycle);

    void checkIsEndedChallenge();
}
