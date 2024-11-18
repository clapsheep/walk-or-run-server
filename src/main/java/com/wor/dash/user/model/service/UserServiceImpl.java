package com.wor.dash.user.model.service;

import java.util.Optional;

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
	public Optional<User> findByUserEmail(String userEmail) {
		System.out.println("유저서비스까지 옴");
		User user = null;
		try {
			user = userMapper.findByUserEmail(userEmail);
//			System.out.println(user.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}

	@Override
	public User addUser(User user) {
		log.debug("UserServiceImpl/addUser");
		userMapper.addUser(user);
		return userMapper.findByUserEmail(user.getUserEmail());
	}

	@Override
	public Optional<User> selectByExampleWithoutPassword(String username) {
		User user = userMapper.selectWithoutPassword(username);
		return Optional.ofNullable(user);
	}

}
