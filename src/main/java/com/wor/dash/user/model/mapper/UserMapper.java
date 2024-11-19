package com.wor.dash.user.model.mapper;


import com.wor.dash.challenge.model.Challenge;
import com.wor.dash.user.model.User;

import java.util.List;

public interface UserMapper {
	
	User selectByUserEmail(String userEmail);
	int insertUser(User user);
	User selectPublicInfo(String userEmail);
	int updateUserRole(int userId);
	int updateUser(User user);
	List<Challenge> selectChallengesByUserId(int userId);
	int deleteUser(int userId);
}
