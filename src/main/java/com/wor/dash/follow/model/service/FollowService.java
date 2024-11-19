package com.wor.dash.follow.model.service;

import java.util.List;

import com.wor.dash.follow.model.Follow;
import com.wor.dash.user.model.User;

public interface FollowService {

	void addFollow(Follow follow);
	boolean removeFollow(Follow follow);
	List<User> getFollowerList(int userId);
	List<User> getFollowingList(int userId);
	boolean checkFollow(Follow follow);
}
