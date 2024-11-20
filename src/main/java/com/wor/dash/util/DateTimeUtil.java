package com.wor.dash.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Component
public class DateTimeUtil {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 현재 시간 반환
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    // 하루 뒤 23:59:59 반환
    public String getOneDayLaterStr() {
        return LocalDateTime.now()
                .plusDays(1)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    // 일주일 뒤 23:59:59 반환
    public String getOneWeekLaterStr() {

        LocalDateTime now = LocalDateTime.now();
        System.out.println("======================================================== 현재 시간 : " + now);
        return now.plusWeeks(1)  // 정확히 7일 후
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    // 한달 뒤 23:59:59 반환
    public String getOneMonthLaterStr() {
        return LocalDateTime.now()
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public int getNowYearStr() {
        return LocalDateTime.now().getYear();
    }

    public int getNowMonthStr() {
        return LocalDateTime.now().getMonthValue();
    }

    public int getNowDayStr() {
        return LocalDateTime.now().getDayOfMonth();
    }
}
