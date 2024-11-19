package com.wor.dash.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.wor.dash.challenge.model.service.ChallengeService;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
    private final ChallengeService challengeService;
    // @Scheduled(cron = "초 분 시 일 월 요일")
    // * : 모든 값
    //? : 설정 없음 (일, 요일에서만 사용)
    //- : 범위 지정
    //, : 여러 값 지정

    /// : 증분값
    //L : 마지막 (일, 요일에서만 사용)
    //W : 가장 가까운 평일 (일에서만 사용)
    //# : N번째 특정 요일 (요일에서만 사용)

    @Scheduled(cron = "0 0 0 * * *")    // 매일 00시
    public void createDailyChallengeTask() {
        try {
            challengeService.addDailyChallenge();
        } catch (Exception e) {
            log.debug("[SchedulerConfig] : createDailyChallengeTask Exception");
        }
    }

    @Scheduled(cron = "0 0 0 * * MON") // 매주 월요일 00시 정각
    public void createWeeklyChallengeTask() {
        try {
            challengeService.addWeeklyChallenge();
        } catch (Exception e) {
            log.debug("[SchedulerConfig] : addWeeklyChallenge Exception");
        }
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void createMonthlyChallengeTask() {
        try {
            challengeService.addMonthlyChallenge();
        } catch (Exception e) {
            log.debug("[SchedulerConfig] : addMonthlyChallenge Exception");
        }
    }

    @Scheduled(cron = "0 0 */3 * * *")
    public void checkChallengeIsEndedTask() {
        try {
            challengeService.checkIsEndedChallenge();
        } catch (Exception e) {
            log.debug("[SchedulerConfig] : checkChallengeIsEndedTask Exception");
        }
    }
}