package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
    private Integer userId;
    private Integer totalCalorie; // 총소모칼로리
    //    private Integer heartRateSampleCount; // 심박수 측정 횟수
//    private Integer duration; // 운동 지속 시간(밀리초)
    private String startTime; // 운동 시작 시간
    private Integer meanHeartRate; // 평균 심박수
    private Integer count; // 걸음수
    private Integer maxHeartRate; // 최대 심박수
    //    private Integer updateTime; // 데이터 업데이트 시간
//    private Integer createTime; // 데이터 생성 시간
    private Integer maxSpeed; // 최대 속도 (m/s)
    private Integer minHeartRate; // 최소 심박수
    private Double distance; // 이동 거리(미터)
    private Double meanSpeed;
    private String endTime;

//    public void setStartTime(String startTime) {
//        this.startTime = Date.parse(startTime, timeFormatter);
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = Date.parse(endTime, timeFormatter);
//    }

}