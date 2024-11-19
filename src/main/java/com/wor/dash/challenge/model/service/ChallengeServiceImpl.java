package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.mapper.ChallengeMapper;
import com.wor.dash.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Challenge> getAllChallenges() {
        return challengeMapper.selectChallengeList();
    }

    @Override
    public List<Challenge> getActiveChallenges() {
        return challengeMapper.selectActiveChallengeList();
    }

    @Override
    public List<Challenge> getEndedChallenges() {
        return challengeMapper.selectEndedChallengeList();
    }

    @Override
    public Challenge getChallengeById(int challengeId) {

        return challengeMapper.selectChallenge(challengeId);
    }

    @Override
    public boolean editChallenge(Challenge challenge) {
        int result = challengeMapper.updateChallenge(challenge);

        return result == 1;
    }

    @Override
    public boolean removeChallenge(int challengeId) {
        int result = challengeMapper.deleteChallenge(challengeId);

        return result == 1;
    }

    @Override
    public void addDailyChallenge() {
        int month = dateTimeUtil.getNowMonthStr();
        int day = dateTimeUtil.getNowDayStr();

        Challenge challenge = new Challenge();
        challenge.setChallengeCategoryCode(2);
        challenge.setChallengeCategoryName("걷거나(걷기)");
        challenge.setChallengeTitle("[Daily] " + month + " / " + day  + " 챌린지");
        challenge.setChallengeDescription("다시 한 번 시작입니다! 같이 걸어봐요!");
        challenge.setChallengeAuthorId(2);
        challenge.setChallengeTargetCnt(100);
        challenge.setChallengeDeleteDate(dateTimeUtil.getOneDayLaterStr());

        challengeMapper.insertChallenge(challenge);
    }

    @Override
    public void addWeeklyChallenge() {
        int month = dateTimeUtil.getNowMonthStr();
        int day = dateTimeUtil.getNowDayStr();

        Challenge challenge = new Challenge();
        challenge.setChallengeCategoryCode(2);
        challenge.setChallengeCategoryName("걷거나(걷기)");
        challenge.setChallengeTitle("[Weekly] " + month + " / " + day  + " 챌린지");
        challenge.setChallengeDescription("다시 한 번 시작입니다! 같이 걸어봐요!");
        challenge.setChallengeAuthorId(2);
        challenge.setChallengeTargetCnt(500);
        challenge.setChallengeDeleteDate(dateTimeUtil.getOneWeekLaterStr());

        challengeMapper.insertChallenge(challenge);
    }

    @Override
    public void addMonthlyChallenge() {
        int month = dateTimeUtil.getNowMonthStr();
        int day = dateTimeUtil.getNowDayStr();

        Challenge challenge = new Challenge();
        challenge.setChallengeCategoryCode(1);
        challenge.setChallengeCategoryName("걷거나(걷기)");
        challenge.setChallengeTitle("[Monthly] " + month + " / " + day  + " 챌린지");
        challenge.setChallengeDescription("한 달 Running Challenge! 모두 할 수 있어요!");
        challenge.setChallengeAuthorId(2);
        challenge.setChallengeTargetCnt(1000);
        challenge.setChallengeDeleteDate(dateTimeUtil.getOneMonthLaterStr());

        challengeMapper.insertChallenge(challenge);
    }


    @Override
    public void checkIsEndedChallenge() {
        String currentTime = dateTimeUtil.getCurrentDateTime();
        challengeMapper.autoDeleteChallenge(currentTime);
    }
}
