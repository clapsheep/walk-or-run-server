package com.wor.dash.user.model.mapper;


import com.wor.dash.user.model.User;

public interface UserMapper {
	User findByUserEmail(String userEmail);
	int addUser(User user);
	User selectWithoutPassword(String userEmail);
}
