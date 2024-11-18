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
public class Speed {
	private int speedId;
	@NonNull private int timeId;
	private double meanSpeed;
	private double maxSpeed;
}
