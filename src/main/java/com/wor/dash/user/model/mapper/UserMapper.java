package com.wor.dash.user.model.mapper;

import com.wor.dash.user.model.MyChallenge;
import com.wor.dash.user.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

	int insertUser(User user);
	User selectPublicInfo(String userEmail);
	int updateUserRole(int userId);
	int updateUser(User user);
	List<MyChallenge> selectChallengesByUserId(@Param("userId") int userId, @Param("offset") int offset, @Param("pageSize") int limit);
	int deleteUser(int userId);
	Integer updateUserPassword(User user);
	User selectUserImportantInfo(String userEmail);
	String selectUserEmailById(int userId);
	List<User> getUserListForSearch(@Param("type") String type, @Param("value") String value, @Param("offset") int offset, @Param("pageSize") int limit);

}
