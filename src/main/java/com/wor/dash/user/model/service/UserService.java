package com.wor.dash.user.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.user.model.MyChallenge;
import com.wor.dash.user.model.User;


public interface UserService {

	User addUser(User user);
	Optional<Integer> getUserId(String userEmail);
	Optional<User> getPublicInfo(int userId);
	Optional<Integer> updateUserRole(int userId);
	Optional<Integer> updateUserInfo(User user);
	Optional<List<MyChallenge>> getChallenges(int userId);
	Optional<Integer> withdrawUser(int userId);
	Boolean checkUserPassword(User user);
	Optional<Integer> updateUserPassword(User user);

}
