package com.wor.dash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.wor.dash"})
public class WalkOrRunServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkOrRunServerApplication.class, args);
    }
}