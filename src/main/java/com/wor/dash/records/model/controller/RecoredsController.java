package com.wor.dash.records.model.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wor.dash.records.model.service.CalorieService;
import com.wor.dash.records.model.service.DistanceService;
import com.wor.dash.records.model.service.HeartRateService;
import com.wor.dash.records.model.service.SpeedService;
import com.wor.dash.records.model.service.StepService;
import com.wor.dash.records.model.service.TimeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/recodes")
@RequiredArgsConstructor
public class RecoredsController {
	private final CalorieService calorieService;
	private final DistanceService distanceService;
	private final HeartRateService heartRateService;
	private final SpeedService speedService;
	private final StepService stepService;
	private final TimeService timeService;
	
}
