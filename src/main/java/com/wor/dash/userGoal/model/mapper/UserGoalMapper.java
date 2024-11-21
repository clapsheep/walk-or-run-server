package com.wor.dash.userGoal.model.mapper;

import com.wor.dash.userGoal.model.UserGoal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGoalMapper {
    List<UserGoal> selectUserGoals(int userId);

    UserGoal selectUserGoalById(int userId, int userGoalId);

    int insertUserGoal(@Param("userId") int userId, @Param("userGoal") UserGoal userGoal);

    int updateUserGoal(@Param("userId") int userId, @Param("userGoalId") int userGoalId, @Param("userGoal") UserGoal userGoal);

    int deleteUserGoal(int userId, int userGoalId);
}
