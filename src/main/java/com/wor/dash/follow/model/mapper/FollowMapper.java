package com.wor.dash.follow.model.mapper;

import java.util.List;

import com.wor.dash.follow.model.Follow;
import com.wor.dash.user.model.User;

public interface FollowMapper {
	List<User> selectFollowerList(int userId);
	List<User> selectFollowingList(int userId);
	int selectFollow(Follow follow);
	void insertFollow(Follow follow);
	int deleteFollow(Follow follow);

}
