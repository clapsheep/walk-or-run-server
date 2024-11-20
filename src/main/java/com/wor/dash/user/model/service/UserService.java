package com.wor.dash.user.model.service;

import java.util.Optional;

import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.password.PasswordChangeUtil;
import com.wor.dash.user.model.MyChallenge;
import com.wor.dash.user.model.User;

public interface UserService {

	User addUser(User user);
	Optional<User> getPublicInfo(String userEmail);
	Optional<Integer> updateUserRole(int userId);
	Optional<Integer> updateUserInfo(User user);
	Optional<PageResponse<MyChallenge>> getChallenges(int userId, int currentPage, int pageSize);
	Optional<Integer> withdrawUser(int userId);
	Boolean checkUserPassword(PasswordChangeUtil passwordChangeUtil);
	Optional<Integer> updateUserPassword(User user);
	String getUserEmail(int userId);
	Optional<User> getUserImportantInfo(String userEmail);

}
