package com.wor.dash.follow.model.service;

import java.util.List;

import com.wor.dash.follow.model.Follow;

public interface FollowService {

	void addFollow(Follow follow);

	boolean removeFollow(int followId);

	List<Follow> getFollowers(int userId);

	List<Follow> getFollowing(int userId);

}
