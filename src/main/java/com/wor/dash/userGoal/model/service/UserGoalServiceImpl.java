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
        log.info("UserGoalService/getUserGoals");
        return userGoalMapper.selectUserGoals(userId);
    }

    @Override
    public UserGoal getUserGoalById(int userId, int userGoalId) {
        log.info("UserGoalService/getUserGoalById");
        return userGoalMapper.selectUserGoalById(userId, userGoalId);
    }

    @Override
    public int addUserGoal(int userId, UserGoal userGoal) {
        log.info("UserGoalService/addUserGoal");
        return userGoalMapper.insertUserGoal(userId, userGoal);
    }

    @Override
    public int editUserGoal(int userId, int userGoalId, UserGoal userGoal) {
        log.info("UserGoalService/editUserGoal");
        return userGoalMapper.updateUserGoal(userId, userGoalId, userGoal);
    }

    @Override
    public int removeUserGoal(int userId, int userGoalId) {
        log.info("UserGoalService/removeUserGoal");
        return userGoalMapper.deleteUserGoal(userId, userGoalId);
    }
}
