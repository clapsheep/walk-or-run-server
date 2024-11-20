package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cadence {
    private Integer recordId;
    private Integer userId;
    private double cadence;
}