package com.wor.dash.user.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.user.model.MyChallenge;
import org.springframework.stereotype.Service;

import com.wor.dash.user.model.User;
import com.wor.dash.user.model.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;
	
	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

	@Override
	public User addUser(User user) {
		log.debug("UserServiceImpl/addUser");
		userMapper.insertUser(user);
		return userMapper.selectPublicInfo(user.getUserId());
	}

	@Override
	public Optional<Integer> getUserId(String userEmail) {
		log.debug("UserServiceImpl/getUserId: " + userMapper.selectUserId(userEmail));
		return Optional.ofNullable(userMapper.selectUserId(userEmail));
	}

	@Override
	public Optional<User> getPublicInfo(int userId) {
		log.debug("UserServiceImpl/getPublicInfo");
		User user = userMapper.selectPublicInfo(userId);
		log.debug("UserServiceImpl/getPublicInfo");
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<Integer> updateUserRole(int userId) {
		return Optional.ofNullable(userMapper.updateUserRole(userId));
	}

	@Override
	public Optional<Integer> updateUserInfo(User user) {
		return Optional.ofNullable(userMapper.updateUser(user));
	}

	@Override
	public Optional<List<MyChallenge>> getChallenges(int userId) {
		List<MyChallenge> challenges = userMapper.selectChallengesByUserId(userId);
		return Optional.ofNullable(challenges);
	}

	@Override
	public Optional<Integer> withdrawUser(int userId) {
		return Optional.ofNullable(userMapper.deleteUser(userId));
	}

}
