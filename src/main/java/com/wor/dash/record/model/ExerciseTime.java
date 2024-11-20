package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseTime {
    private int recordId;
    private int userId;
    private int startTime;
    private int endTime;
    private int timeInterval;
}

