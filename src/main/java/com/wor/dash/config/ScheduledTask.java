package com.wor.dash.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    public void executeTask() {
        System.out.println("Scheduled Task Executed at " + new Date());
    }
}
