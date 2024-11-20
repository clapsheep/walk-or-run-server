package com.wor.dash.follow.model.mapper;

import com.wor.dash.user.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {
    List<User> selectFollowerList(int userId);

    List<User> selectFollowingList(int userId);

    int selectFollow(@Param("userId") int userId, @Param("targetUserId") int targetUserId);

    void insertFollow(@Param("userId") int userId, @Param("targetUserId") int targetUserId);

    int deleteFollow(@Param("userId") int userId, @Param("targetUserId") int targetUserId);

}
