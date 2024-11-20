package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.mapper.ChallengeMapper;
import com.wor.dash.pageInfo.model.PageInfo;
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
        List<Challenge> challenges = challengeMapper.selectAllChallengeList(currentPage, pageSize);
        int totalElements = challenges.size();
        int offset = (currentPage - 1) * pageSize;
        PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
        return new PageResponse<>(challenges, pageInfo);
    }

    @Override
    public PageResponse<Challenge> getActiveChallenges(int currentPage, int pageSize) {
        List<Challenge> challenges = challengeMapper.selectActiveChallengeList(currentPage, pageSize);
        int totalElements = challenges.size();
        int offset = (currentPage - 1) * pageSize;
        PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
        return new PageResponse<>(challenges, pageInfo);
    }

    @Override
    public PageResponse<Challenge> getEndedChallenges(int currentPage, int pageSize) {
        List<Challenge> challenges = challengeMapper.selectEndedChallengeList(currentPage, pageSize);
        int totalElements = challenges.size();
        int offset = (currentPage - 1) * pageSize;
        PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
        return new PageResponse<>(challenges, pageInfo);
    }

    @Override
    public Challenge getChallengeById(int challengeId) {

        return challengeMapper.selectChallenge(challengeId);
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
        challengeMapper.addDailyChallenge(challenge_scheduler_cycle);
    }

    @Override
    public void addWeeklyChallenge(int challenge_scheduler_cycle) {
        challengeMapper.addWeeklyChallenge(challenge_scheduler_cycle);
    }

    @Override
    public void addMonthlyChallenge(int challenge_scheduler_cycle) {
        challengeMapper.addMonthlyChallenge(challenge_scheduler_cycle);
    }


    @Override
    public void checkIsEndedChallenge() {
        String currentTime = dateTimeUtil.getCurrentDateTime();
        challengeMapper.autoDeleteChallenge(currentTime);
    }
}
