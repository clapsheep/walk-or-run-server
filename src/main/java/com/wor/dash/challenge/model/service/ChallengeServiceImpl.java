package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.mapper.ChallengeMapper;
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
    public List<Challenge> getAllChallengeList() {
        return challengeMapper.selectAllChallengeList();
    }

    @Override
    public List<Challenge> getActiveChallengeList() {
        return challengeMapper.selectActiveChallengeList();
    }

    @Override
    public List<Challenge> getEndedChallengeList() {
        return challengeMapper.selectEndedChallengeList();
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
