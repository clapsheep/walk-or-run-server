package com.wor.dash.user.model.service;

import java.util.Optional;

import com.wor.dash.user.model.User;

public interface UserService {

	Optional<User> findByUserEmail(String userEmail);

	User addUser(User user);
	
	Optional<User> selectByExampleWithoutPassword(String userEmail);
}
