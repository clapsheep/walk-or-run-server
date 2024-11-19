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
    private int totalCalorie; // 총소모칼로리
    private int heartRateSampleCount; // 심박수 측정 횟수
    private int duration; // 운동 지속 시간(밀리초)
    private int startTime; // 운동 시작 시간
    private int meanHeartRate; // 평균 심박수
    private int count; // 걸음수
    private int maxHeartRate; // 최대 심박수
    private int updateTime; // 데이터 업데이트 시간
    private int createTime; // 데이터 생성 시간
    private int maxSpeed; // 최대 속도 (m/s)
    private int minHeartRate; // 최소 심박수
    private int distance; // 이동 거리(미터)
    private int meanSpeed;
}
