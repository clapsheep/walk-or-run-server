package com.wor.dash.follow.model.service;

import java.util.List;

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
	public boolean removeFollow(int followId) {
		int result = followMapper.deleteMapper(followId);
		
		return result == 1;
	}

	@Override
	public List<Follow> getFollowers(int userId) {
		
		return followMapper.selectFollowers(userId);
	}

	@Override
	public List<Follow> getFollowing(int userId) {

		return followMapper.selectFollowing(userId);
	}

}
