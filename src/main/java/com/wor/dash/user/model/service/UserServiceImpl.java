package com.wor.dash.user.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.pageInfo.model.PageInfo;
import com.wor.dash.pageInfo.model.PageResponse;
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
		log.info("UserService/addUser");
		userMapper.insertUser(user);
		return userMapper.selectPublicInfo(user.getUserEmail());
	}

	@Override
	public Optional<User> getPublicInfo(String userEmail) {
		log.info("UserService/getPublicInfo");
		User user = userMapper.selectPublicInfo(userEmail);
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<Integer> updateUserRole(int userId) {
		log.info("UserService/updateUserRole");
		return Optional.ofNullable(userMapper.updateUserRole(userId));
	}

	@Override
	public Optional<Integer> updateUserInfo(User user) {
		log.info("UserService/updateUserInfo");
		return Optional.ofNullable(userMapper.updateUser(user));
	}

	@Override
	public Optional<PageResponse<MyChallenge>> getChallenges(int userId, int currentPage, int pageSize) {
		log.info("UserService/getChallenges");
		List<MyChallenge> challenges = userMapper.selectChallengesByUserId(userId, currentPage, pageSize);
		int totalElements = challenges.size();
		int offset = (currentPage - 1) * pageSize;
		PageInfo pageInfo = new PageInfo(currentPage, pageSize, totalElements);
		return Optional.ofNullable(new PageResponse<>(challenges, pageInfo));
	}

	@Override
	public Optional<Integer> withdrawUser(int userId) {
		log.info("UserService/withdrawUser");
		return Optional.ofNullable(userMapper.deleteUser(userId));
	}

	@Override
	public
	Boolean checkUserPassword(PasswordChangeUtil user) {
		log.info("UserService/checkUserPassword");
		String email = userMapper.selectUserEmailById(user.getUserId());
		User curUser = userMapper.selectUserImportantInfo(email);
		String encodedPassword = curUser.getUserPassword();
		boolean isCorrectAnswer = user.getUserPasswordAnswer().equals(curUser.getUserPasswordAnswer());
		if(!isCorrectAnswer) {
			return false;
		}
		return passwordEncoder.matches(user.getUserPassword(), encodedPassword);
	}

	@Override
	public
	Optional<Integer> updateUserPassword(User user) {
		log.info("UserService/updateUserPassword");
		return Optional.ofNullable(userMapper.updateUserPassword(user));
	}

	@Override
	public String getUserEmail(int userId) {
		log.info("UserService/getUserEmail");
		return userMapper.selectUserEmailById(userId);
	}

	@Override
	public Optional<User> getUserImportantInfo(String userEmail) {
		log.info("UserService/getUserImportantInfo");
		return Optional.ofNullable(userMapper.selectUserImportantInfo(userEmail));
	}

	@Override
	public Optional<String> findEmail(User user) {
		log.info("UserService/findEmail");
		return Optional.ofNullable(userMapper.selectUserEmailByInfo(user));
	}

	@Override
	public Optional<Integer> checkUserEmail(String userEmail) {
		log.info("UserService/checkUserEmail");
		return Optional.ofNullable(userMapper.selectUserEmailCount(userEmail));
	}

}
