package com.wor.dash.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
    private final ScheduledTask scheduledTask;

    @Scheduled(fixedRate = 10000)
    public void runScheduledTask() {
        scheduledTask.executeTask();
    }
}