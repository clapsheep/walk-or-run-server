package com.wor.dash.follow.model.service;

import com.wor.dash.follow.model.mapper.FollowMapper;
import com.wor.dash.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;

    @Override
    public void addFollow(int userId, int targetUserId) {
        followMapper.insertFollow(userId, targetUserId);
    }

    @Override
    public boolean removeFollow(int userId, int targetUserId) {
        int result = followMapper.deleteFollow(userId, targetUserId);
        return result == 1;
    }

    @Override
    public List<User> getFollowerList(int userId) {
        return followMapper.selectFollowerList(userId);
    }

    @Override
    public List<User> getFollowingList(int userId) {
        return followMapper.selectFollowingList(userId);
    }

    @Override
    public boolean checkFollow(int userId, int targetUserId) {
        int res = followMapper.selectFollow(userId, targetUserId);
        return res == 1;
    }

}