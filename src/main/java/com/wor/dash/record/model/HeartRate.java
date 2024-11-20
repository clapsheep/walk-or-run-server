package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeartRate {
    private int recordId;
    private int userId;
    private int maxHeartRate;
    private int minHeartRate;
    private int meanHeartRate;
    private String startTime;
}
