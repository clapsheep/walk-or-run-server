package com.wor.dash.userGoal.model.service;

import com.wor.dash.userGoal.model.UserGoal;
import com.wor.dash.userGoal.model.mapper.UserGoalMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserGoalServiceImpl implements UserGoalService {
    private final UserGoalMapper userGoalMapper;

    public UserGoalServiceImpl(UserGoalMapper userGoalMapper) {
        this.userGoalMapper = userGoalMapper;
    }

    @Override
    public List<UserGoal> getUserGoals(int userId) {
        return userGoalMapper.selectUserGoals(userId);
    }

    @Override
    public UserGoal getUserGoalById(int userId, int userGoalId) {
        return userGoalMapper.selectUserGoalById(userId, userGoalId);
    }

    @Override
    public int addUserGoal(int userId, UserGoal userGoal) {
        return userGoalMapper.insertUserGoal(userId, userGoal);
    }

    @Override
    public int editUserGoal(int userId, int userGoalId, UserGoal userGoal) {
        return userGoalMapper.updateUserGoal(userId, userGoalId, userGoal);
    }

    @Override
    public int removeUserGoal(int userId, int userGoalId) {
        return userGoalMapper.deleteUserGoal(userId, userGoalId);
    }
}