package com.wor.dash.user.model;

import com.wor.dash.pageInfo.model.PageResponse;
import com.wor.dash.userGoal.model.UserGoal;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
	@NonNull private int userId;
	@NonNull private String userPassword;
	@NonNull private String userName;
	@NonNull private String userEmail;
	@NonNull private String userNickname;
	@NonNull private String userPhoneNumber;
	private String userJoinDate;
	private String userWithdrawalDate;
	private int userWithdrawalStatus;
	private String userRole;
	private int userPasswordQuestionId;
	private String userPasswordQuestionDesc;
	private String userPasswordAnswer;
	private PageResponse<MyChallenge> challenges;
	private
	List<UserGoal> goals;
}
