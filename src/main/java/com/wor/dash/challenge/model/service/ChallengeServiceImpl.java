package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.mapper.ChallengeMapper;
import com.wor.dash.pageInfo.model.PageInfo;
import com.wor.dash.user.model.User;
import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeMapper challengeMapper;
    private final DateTimeUtil dateTimeUtil;

    @Override
    public void addChallenge(Challenge challenge) {
        challengeMapper.insertChallenge(challenge);

    }

    @Override
    public PageResponse<Challenge> getAllChallenges(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Challenge> challenges = challengeMapper.selectAllChallengeList(offset, pageSize);
        int totalElements = challengeMapper.selectAllChallengesCount();
        PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
        return new PageResponse<>(challenges, pageInfo);
    }

    @Override
    public PageResponse<Challenge> getActiveChallenges(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Challenge> challenges = challengeMapper.selectActiveChallengeList(offset, pageSize);
        int totalElements = challengeMapper.selectActiveChallengesCount();
        PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
        return new PageResponse<>(challenges, pageInfo);
    }

    @Override
    public PageResponse<Challenge> getEndedChallenges(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Challenge> challenges = challengeMapper.selectEndedChallengeList(offset, pageSize);
        int totalElements = challengeMapper.selectEndedChallengesCount();
        PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
        return new PageResponse<>(challenges, pageInfo);
    }

    @Override
    public Challenge getChallengeById(int challengeId, int userId) {

        return challengeMapper.selectChallenge(challengeId, userId);
    }

    @Override
    public boolean editChallenge(int challengeId, Challenge challenge) {
        int result = challengeMapper.updateChallenge(challengeId, challenge);

        return result == 1;
    }

    @Override
    public boolean removeChallenge(int challengeId) {
        int result = challengeMapper.deleteChallenge(challengeId);

        return result == 1;
    }

    @Override
    public void addDailyChallenge(int challenge_scheduler_cycle) {
        challengeMapper.insertDailyChallenge(challenge_scheduler_cycle);
    }

    @Override
    public void addWeeklyChallenge(int challenge_scheduler_cycle) {
        challengeMapper.insertWeeklyChallenge(challenge_scheduler_cycle);
    }

    @Override
    public void addMonthlyChallenge(int challenge_scheduler_cycle) {
        challengeMapper.insertMonthlyChallenge(challenge_scheduler_cycle);
    }


    @Override
    public void checkIsEndedChallenge() {
        String currentTime = dateTimeUtil.getCurrentDateTime();
        challengeMapper.autoDeleteChallenge(currentTime);
    }

    @Override
    public boolean registerChallenge(User user, int challengeId) {
        int result = challengeMapper.insertChallengeParticipant(user, challengeId);
        return result == 1;
    }

    @Override
    public boolean cancelChallenge(User user, int challengeId) {
        int result = challengeMapper.deleteChallengeParticipant(user, challengeId);
        return result == 1;
    }

    @Override
    public void addChallengeSchedule(Challenge challenge) {
        challengeMapper.insertChallengeSchedule(challenge);
    }

    @Override
    public boolean editChallengeSchedule(int challengeId, Challenge challenge) {
        int result = challengeMapper.updateChallengeSchedule(challengeId, challenge);
        return result == 1;
    }

    @Override
    public boolean deleteChallengeSchedule(int challengeId) {
        int result = challengeMapper.deleteChallengeSchedule(challengeId);
        return result == 1;
    }

    @Override
    public List<Challenge> getAllChallengeScheduleList() {
        return challengeMapper.selectAllChallengeScheduleList();
    }

    @Override
    public List<Challenge> getActiveChallengeScheduleList() {
        return challengeMapper.selectActiveChallengeScheduleList();
    }

    @Override
    public List<Challenge> getEndedChallengeScheduleList() {
        return challengeMapper.selectEndedChallengeScheduleList();
    }
}