package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Speed {
    private int recordId;
    private int userId;
    private double meanSpeed;
    private double maxSpeed;
    private String startTime;
}
