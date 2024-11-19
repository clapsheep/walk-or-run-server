package com.wor.dash.user.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.challenge.model.Challenge;
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
	public Optional<User> getUser(String userEmail) {
		System.out.println("유저서비스까지 옴");
		User user = null;
		try {
			user = userMapper.selectByUserEmail(userEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}

	@Override
	public User addUser(User user) {
		log.debug("UserServiceImpl/addUser");
		userMapper.insertUser(user);
		return userMapper.selectByUserEmail(user.getUserEmail());
	}

	@Override
	public Optional<User> getPublicInfo(String username) {
		User user = userMapper.selectPublicInfo(username);
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
	public Optional<List<Challenge>> getChallenges(int userId) {
		List<Challenge> challenges = userMapper.selectChallengesByUserId(userId);
		return Optional.ofNullable(challenges);
	}

	@Override
	public Optional<Integer> withdrawUser(int userId) {
		return Optional.ofNullable(userMapper.deleteUser(userId));
	}

}
