package com.wor.dash.user.model.mapper;

import com.wor.dash.user.model.MyChallenge;
import com.wor.dash.user.model.User;

import java.util.List;

public interface UserMapper {

	int insertUser(User user);
	User selectPublicInfo(int userId);
	int updateUserRole(int userId);
	int updateUser(User user);
	List<MyChallenge> selectChallengesByUserId(int userId);
	int deleteUser(int userId);
	Integer selectUserId(String userEmail);
	Integer updateUserPassword(User user);

}
