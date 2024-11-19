package com.wor.dash.challenge.model.service;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.mapper.ChallengeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeMapper challengeMapper;


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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deleteTime = now.withHour(23)
                .withMinute(59)
                .withSecond(59);

        String deleteTimeStr = deleteTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        Challenge challenge = new Challenge();
        challenge.setChallengeCategoryCode(2);
        challenge.setChallengeCategoryName("걷거나(걷기)");
        challenge.setChallengeTitle("[일일] " + month + " / " + day  + " 챌린지");
        challenge.setChallengeDescription("다시 한 번 시작입니다! 같이 걸어봐요!");
        challenge.setChallengeAuthorId(2);
        challenge.setChallengeTargetCnt(300);
        challenge.setChallengeDeleteDate(deleteTimeStr);

        challengeMapper.insertChallenge(challenge);
    }

    @Override
    public void addWeeklyChallenge() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deleteTime = now.withHour(23)
                .withMinute(59)
                .withSecond(59);

        String deleteTimeStr = deleteTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        Challenge challenge = new Challenge();
        challenge.setChallengeCategoryCode(2);
        challenge.setChallengeCategoryName("걷거나(걷기)");
        challenge.setChallengeTitle("[일일] " + month + " / " + day  + " 챌린지");
        challenge.setChallengeDescription("다시 한 번 시작입니다! 같이 걸어봐요!");
        challenge.setChallengeAuthorId(2);
        challenge.setChallengeTargetCnt(300);
        challenge.setChallengeDeleteDate(deleteTimeStr);

        challengeMapper.insertChallenge(challenge);
    }

    @Override
    public void addMonthlyChallenge() {

    }


    @Override
    public void checkIsEndedChallenge() {

    }
}
