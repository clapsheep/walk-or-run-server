package com.wor.dash.userGoal.model.service;

import com.wor.dash.userGoal.model.UserGoal;

import java.util.List;

public interface UserGoalService {
    List<UserGoal> getUserGoals(int userId);

    UserGoal getUserGoalById(int userGoalId);

    int addUserGoal(UserGoal userGoal);

    int updateUserGoal(UserGoal userGoal);

    int deleteUserGoal(int userGoalId);
}
