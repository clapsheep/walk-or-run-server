package com.wor.dash.user.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.user.model.User;


public interface UserService {

	Optional<User> getUser(String userEmail);
	User addUser(User user);
	Optional<User> getPublicInfo(String userEmail);
	Optional<Integer> updateUserRole(int userId);
	Optional<Integer> updateUserInfo(User user);
	Optional<List<Challenge>> getChallenges(int userId);
	Optional<Integer> withdrawUser(int userId);

}
