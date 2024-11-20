package com.wor.dash.user.model;

import java.util.List;

import lombok.*;

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
	private List<MyChallenge> myChallenges;
}
