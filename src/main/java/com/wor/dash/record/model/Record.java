package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Integer totalCalorie; // 총소모칼로리0
    //    private Integer heartRateSampleCount; // 심박수 측정 횟수
    private Integer duration; // 운동 지속 시간(밀리초)
    private String startTime; // 운동 시작 시간0
    private Integer meanHeartRate; // 평균 심박수0
    private Integer maxHeartRate; // 최대 심박수0
    //    private String updateTime; // 데이터 업데이트 시간
//    private String createTime; // 데이터 생성 시간
    private Integer maxSpeed; // 최대 속도 (m/s)0
    private Integer minHeartRate; // 최소 심박수0
    private Integer count; // 걸음수0
    private Double distance; // 이동 거리(미터)0
    private String meanSpeed; // 0
    private String endTime; // 0
}
