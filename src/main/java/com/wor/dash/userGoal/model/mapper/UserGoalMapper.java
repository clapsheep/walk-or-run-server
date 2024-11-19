package com.wor.dash.userGoal.model.mapper;

import com.wor.dash.userGoal.model.UserGoal;

import java.util.List;

public interface UserGoalMapper {
    List<UserGoal> selectUserGoals(int userId);

    UserGoal selectUserGoalById(int userGoalId);

    int insertUserGoal(UserGoal userGoal);

    int updateUserGoal(UserGoal userGoal);

    int deleteUserGoal(int userGoalId);
}
