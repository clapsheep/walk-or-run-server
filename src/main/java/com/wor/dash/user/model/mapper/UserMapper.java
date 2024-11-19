package com.wor.dash.user.model.mapper;


import com.wor.dash.user.model.User;

public interface UserMapper {
	
	User selectByUserEmail(String userEmail);
	int insertUser(User user);
	User selectWithoutPassword(String userEmail);
	int updateUserRole(int userId);

}
