package com.wor.dash.config;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.challenge.model.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ChallengeService challengeService;

    @Bean
    public Job dailyChallenge() {
        return new JobBuilder("dailyChallengeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(createDailyChallengeStep())
                .next(endDailyChallengeStep())
                .build();
    }

    @Bean
    public Step endDailyChallengeStep() {
        return new StepBuilder("endDailyChallengeStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    LocalDateTime now = LocalDateTime.now();
                    String currentTime = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

                    // 현재 시간이 deleteDate를 지난 챌린지들을 종료 상태로 변경
                    challengeService.endChallenges(currentTime);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
    
    @Bean
    public Step createDailyChallengeStep() {
        return new StepBuilder("createDailyChallengeStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    LocalDateTime now = LocalDateTime.now();

                    String createDate = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    String deleteDate = now.plusDays(1).withHour(23).withMinute(59)
                            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

                    // 일일 챌린지 제목
                    String dailyTitle = String.format("%s 일일 챌린지",
                            now.format(DateTimeFormatter.ofPattern("MM월 dd일")));

                    Challenge challenge = Challenge.builder()
                            .challengeCategoryCode(1)  // 카테고리 코드 설정 필요
                            .challengeTitle(dailyTitle)
                            .challengeDescription("매일 매일 도전하는 챌린지입니다!")  // 설명 추가
                            .challengeAuthorId(1)  // 관리자 ID 설정 필요
                            .challengeParticipantCnt(0)  // 초기 참여자 수 0
                            .challengeTargetCnt(1)  // 목표 달성 횟수
                            .challengeCreateDate(createDate)
                            .challengeDeleteDate(deleteDate)
                            .challengeIsEnded(0)
                            .build();

                    challengeService.addDailyChallenge(challenge);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step createWeeklyChallengeStep() {
        return new StepBuilder("createWeeklyChallengeStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime weekEnd = now.plusDays(6);

                    String createDate = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

                    // 주간 챌린지 제목
                    String weeklyTitle = String.format("%s ~ %s 주간 챌린지",
                            now.format(DateTimeFormatter.ofPattern("MM월 dd일")),
                            weekEnd.format(DateTimeFormatter.ofPattern("MM월 dd일")));

                    Challenge challenge = Challenge.builder()
                            .challengeCategoryCode(2)  // 카테고리 코드 설정
                            .challengeTitle(weeklyTitle)
                            .challengeDescription("일주일 동안 도전하는 챌린지입니다!")
                            .challengeAuthorId(1)
                            .challengeParticipantCnt(0)
                            .challengeTargetCnt(7)  // 7일 동안의 목표
                            .challengeCreateDate(createDate)
                            .challengeIsEnded(0)
                            .build();

                    challengeService.addWeeklyChallenge(challenge);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step createMonthlyChallengeStep() {
        return new StepBuilder("createMonthlyChallengeStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime weekEnd = now.plusDays(7);

                    String createDate = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    String deleteDate = weekEnd.withHour(23).withMinute(59)
                            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

                    // 월간 챌린지 제목
                    String monthlyTitle = String.format("%s 월간 챌린지",
                            now.format(DateTimeFormatter.ofPattern("MM월")));

                    Challenge challenge = Challenge.builder()
                            .challengeCategoryCode(3)  // 카테고리 코드 설정
                            .challengeTitle(monthlyTitle)
                            .challengeDescription("한 달 동안 도전하는 챌린지입니다!")
                            .challengeAuthorId(1)
                            .challengeParticipantCnt(0)
                            .challengeTargetCnt(30)  // 30일 기준
                            .challengeCreateDate(createDate)
                            .challengeDeleteDate(deleteDate)
                            .challengeIsEnded(0)
                            .build();

                    challengeService.addMonthChallenge(challenge);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
