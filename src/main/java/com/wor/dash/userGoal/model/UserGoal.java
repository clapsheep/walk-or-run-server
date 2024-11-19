package com.wor.dash.userGoal.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class UserGoal {
    @NonNull
    private int userGoalId;
    @NonNull
    private int userId;
    @NonNull
    private int challengeCategoryCode;
    @NonNull
    private int challengeCategoryUnitCode;
    @NonNull
    private  String  startDate;
    @NonNull
    private  String  endDate;
    @NonNull
    private double targetAmount;

    private double currentAmount;
    private String challengeCategoryName;
    private String challengeCategoryUnitName;


}
