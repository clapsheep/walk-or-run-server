package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class HeartRate {
	private int heartRateId;
	@NonNull private int timeId;
	private int maxHeartRate;
	private int minHeartRate;
	private int meanHeartRate;
}
