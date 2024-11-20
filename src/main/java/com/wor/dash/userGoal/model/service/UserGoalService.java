package com.wor.dash.userGoal.model.service;

import com.wor.dash.userGoal.model.UserGoal;

import java.util.List;

public interface UserGoalService {
    List<UserGoal> getUserGoals(int userId);

    UserGoal getUserGoalById(int userId, int userGoalId);

    int addUserGoal(int userId, UserGoal userGoal);

    int editUserGoal(int userId, int userGoalId, UserGoal userGoal);

    int removeUserGoal(int userId, int userGoalId);
}
