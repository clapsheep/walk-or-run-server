package com.wor.dash.record.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
//@RequiredArgsConstructor
//@NoArgsConstructor
public class Calorie {
	private int calorieId;
	@NonNull private int timeId;
	private double totalCalorie;
	private double calorieDay;

//	public Calorie(int calorieId, @NonNull int timeId, double totalCalorie, double calorieDay) {
//		this.calorieId = calorieId;
//		this.timeId = timeId;
//		this.totalCalorie = totalCalorie;
//		this.calorieDay = calorieDay;
//	}
//
//	public Calorie(@NonNull int timeId, double totalCalorie, double calorieDay) {
//		this.timeId = timeId;
//		this.totalCalorie = totalCalorie;
//		this.calorieDay = calorieDay;
//	}
//
//	public Calorie(@NonNull int timeId, double calorieDay) {
//		this.timeId = timeId;
//		this.calorieDay = calorieDay;
//	}
//
//	public Calorie() {
//
//	}
//
//	@Override
//	public String toString() {
//		return "Calorie{" +
//				"calorieId=" + calorieId +
//				", timeId=" + timeId +
//				", totalCalorie=" + totalCalorie +
//				", calorieDay=" + calorieDay +
//				'}';
//	}
//
//	public int getCalorieId() {
//		return calorieId;
//	}
//
//	public void setCalorieId(int calorieId) {
//		this.calorieId = calorieId;
//	}
//
//	public @NonNull int getTimeId() {
//		return timeId;
//	}
//
//	public void setTimeId(@NonNull int timeId) {
//		this.timeId = timeId;
//	}
//
//	public double getTotalCalorie() {
//		return totalCalorie;
//	}
//
//	public void setTotalCalorie(double totalCalorie) {
//		this.totalCalorie = totalCalorie;
//	}
//
//	public double getCalorieDay() {
//		return calorieDay;
//	}
//
//	public void setCalorieDay(double calorieDay) {
//		this.calorieDay = calorieDay;
//	}
}
