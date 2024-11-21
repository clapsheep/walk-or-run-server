package com.wor.dash.follow.model.service;

import com.wor.dash.user.model.User;

import java.util.List;

public interface FollowService {

    void addFollow(int userId, int targetUserId);

    boolean removeFollow(int userId, int targetUserId);

    List<User> getFollowerList(int userId);

    List<User> getFollowingList(int userId);

    boolean checkFollow(int userId, int targetUserId);
}
