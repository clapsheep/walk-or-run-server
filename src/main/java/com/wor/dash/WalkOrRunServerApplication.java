package com.wor.dash;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
public class WalkOrRunServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkOrRunServerApplication.class, args);
    }
}