package com.wor.dash.user.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wor.dash.user.model.User;
import com.wor.dash.user.model.dao.UserMapper;

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
	public Optional<User> findByUserEmail(String userEmail) {
		User user = userMapper.findByUserEmail(userEmail);
		return Optional.ofNullable(user);
	}

	@Override
	public User addUser(User user) {
		userMapper.addUser(user);
		return userMapper.findByUserEmail(user.getUserEmail());
	}

	@Override
	public Optional<User> selectByExampleWithoutPassword(String username) {
		User user = userMapper.selectWithoutPassword(username);
		return Optional.ofNullable(user);
	}

}
