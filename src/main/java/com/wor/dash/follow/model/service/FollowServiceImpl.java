package com.wor.dash.follow.model.service;

import java.util.List;

import com.wor.dash.user.model.User;
import org.springframework.stereotype.Service;

import com.wor.dash.follow.model.Follow;
import com.wor.dash.follow.model.mapper.FollowMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

	private final FollowMapper followMapper;

	@Override
	public void addFollow(Follow follow) {
		followMapper.insertFollow(follow);
	}

	@Override
	public boolean removeFollow(Follow follow) {
		int result = followMapper.deleteFollow(follow);
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
	public boolean checkFollow(Follow follow) {
		int res = followMapper.selectFollow(follow);
		return res == 1;
	}

}