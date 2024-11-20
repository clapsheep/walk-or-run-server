package com.wor.dash.follow.model.service;

import com.wor.dash.follow.model.mapper.FollowMapper;
import com.wor.dash.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;

    @Override
    public void addFollow(int userId, int targetUserId) {
        log.info("FollowService/addFollow");
        followMapper.insertFollow(userId, targetUserId);
    }

    @Override
    public boolean removeFollow(int userId, int targetUserId) {
        log.info("FollowService/removeFollow");
        int result = followMapper.deleteFollow(userId, targetUserId);
        return result == 1;
    }

    @Override
    public List<User> getFollowerList(int userId) {
        log.info("FollowService/getFollwerList");
        return followMapper.selectFollowerList(userId);
    }

    @Override
    public List<User> getFollowingList(int userId) {
        log.info("FollowService/getFollowingList");
        return followMapper.selectFollowingList(userId);
    }

    @Override
    public boolean checkFollow(int userId, int targetUserId) {
        log.info("FollowService/checkFollow");
        int res = followMapper.selectFollow(userId, targetUserId);
        return res == 1;
    }

}