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
    private int goalCount;

    private double goalRate;
    private String challengeCategoryName;
    private String challengeCategoryUnitName;


}
