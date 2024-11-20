package com.wor.dash.user.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.password.PasswordChangeUtil;
import com.wor.dash.user.model.MyChallenge;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wor.dash.user.model.User;
import com.wor.dash.user.model.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final  UserMapper userMapper;

	@Override
	public User addUser(User user) {
		log.debug("UserServiceImpl/addUser");
		userMapper.insertUser(user);
		return userMapper.selectPublicInfo(user.getUserEmail());
	}

	@Override
	public Optional<User> getPublicInfo(String userEmail) {
		log.debug("UserServiceImpl/getPublicInfo");
		User user = userMapper.selectPublicInfo(userEmail);
		log.debug("UserServiceImpl/getPublicInfo");
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<Integer> updateUserRole(int userId) {
		return Optional.ofNullable(userMapper.updateUserRole(userId));
	}

	@Override
	public Optional<Integer> updateUserInfo(User user) {
		log.debug("UserServiceImpl/updateUserInfo");
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

	@Override
	public
	Boolean checkUserPassword(PasswordChangeUtil user) {
		log.debug("UserServiceImpl/checkUserPassword");
		String email = userMapper.selectUserEmailById(user.getUserId());
		User curUser = userMapper.selectUserImportantInfo(email);
		String encodedPassword = curUser.getUserPassword();
		System.out.println(curUser.toString());
		System.out.println(user.getUserPasswordAnswer());
		boolean isCorrectAnswer = user.getUserPasswordAnswer().equals(curUser.getUserPasswordAnswer());
		if(!isCorrectAnswer) {
			return false;
		}
		return passwordEncoder.matches(user.getUserPassword(), encodedPassword);
	}

	@Override
	public
	Optional<Integer> updateUserPassword(User user) {
		log.debug("UserServiceImpl/updateUserPassword");
		return Optional.ofNullable(userMapper.updateUserPassword(user));
	}

	@Override
	public String getUserEmail(int userId) {
		log.debug("UserServiceImpl/getUserEmail");
		return userMapper.selectUserEmailById(userId);
	}

	@Override
	public Optional<User> getUserImportantInfo(String userEmail) {
		return Optional.ofNullable(userMapper.selectUserImportantInfo(userEmail));
	}

}
