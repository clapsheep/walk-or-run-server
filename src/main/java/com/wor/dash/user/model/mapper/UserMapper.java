package com.wor.dash.user.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.wor.dash.user.model.User;

@Mapper
public interface UserMapper {
	
	User findByUserEmail(String userEmail);
	
	
	int addUser(User user);
	
	
	User selectWithoutPassword(String userEmail);
}
