package com.wor.dash.challenge.model.mapper;

import com.wor.dash.challenge.model.Challenge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChallengeMapper {
    void insertChallenge(Challenge challenge);

    List<Challenge> selectAllChallengeList(@Param("offset") int offset, @Param("pageSize") int limit);

    List<Challenge> selectActiveChallengeList(@Param("offset") int offset, @Param("pageSize") int limit);

    List<Challenge> selectEndedChallengeList(@Param("offset") int offset, @Param("pageSize") int limit);

    Challenge selectChallenge(int challengeId);

    int updateChallenge(int challengeId, Challenge challenge);

    int deleteChallenge(int challengeId);

    void autoDeleteChallenge(String endTime);

    void addDailyChallenge(int challengeSchedulerCycle);

    void addWeeklyChallenge(int challengeSchedulerCycle);

    void addMonthlyChallenge(int challengeSchedulerCycle);
}
