package com.wor.dash.follow.model.mapper;

import java.util.List;

import com.wor.dash.follow.model.Follow;

public interface FollowMapper {

	void insertFollow(Follow follow);
	int deleteMapper(int followId);
	List<Follow> selectFollowers(int userId);
	List<Follow> selectFollowing(int userId);

}
