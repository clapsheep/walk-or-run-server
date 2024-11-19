package com.wor.dash.record.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wor.dash.record.model.service.CalorieService;
import com.wor.dash.record.model.service.DistanceService;
import com.wor.dash.record.model.service.HeartRateService;
import com.wor.dash.record.model.service.SpeedService;
import com.wor.dash.record.model.service.StepService;
import com.wor.dash.record.model.service.TimeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/record")
@RequiredArgsConstructor
public class RecordController {
	private final CalorieService calorieService;
	private final DistanceService distanceService;
	private final HeartRateService heartRateService;
	private final SpeedService speedService;
	private final StepService stepService;
	private final TimeService timeService;
	
}
