package com.wor.dash.challenge.model.mapper;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.ChallengeCategory;
import com.wor.dash.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChallengeMapper {
    void insertChallenge(Challenge challenge);

    List<Challenge> selectAllChallengeList(@Param("offset") int offset, @Param("pageSize") int limit);

    List<Challenge> selectActiveChallengeList(@Param("offset") int offset, @Param("pageSize") int limit);

    List<Challenge> selectEndedChallengeList(@Param("offset") int offset, @Param("pageSize") int limit);

    Challenge selectChallenge(@Param("challengeId") int challengeId);

    int updateChallenge(@Param("challengeId") int challengeId, @Param("challenge") Challenge challenge);

    int deleteChallenge(int challengeId);

    void autoDeleteChallenge(String endTime);

    void insertDailyChallenge(int challengeSchedulerCycle);

    void insertWeeklyChallenge(int challengeSchedulerCycle);

    void insertMonthlyChallenge(int challengeSchedulerCycle);

    int insertChallengeParticipant(@Param("user") User user, @Param("challengeId") int challengeId);

    int deleteChallengeParticipant(@Param("user") User user, @Param("challengeId") int challengeId);

    void insertChallengeSchedule(Challenge challenge);

    int updateChallengeSchedule(@Param("challengeId") int challengeId, @Param("challenge") Challenge challenge);

    int deleteChallengeSchedule(int challengeId);

    List<Challenge> selectAllChallengeScheduleList();

    List<Challenge> selectActiveChallengeScheduleList();

    List<Challenge> selectEndedChallengeScheduleList();

    int selectAllChallengesCount();

    int selectActiveChallengesCount();

    int selectEndedChallengesCount();

    List<ChallengeCategory> selectChallengeCategories();

    Challenge selectChallengeSchedule(int challengeId);
}