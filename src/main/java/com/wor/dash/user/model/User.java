package com.wor.dash.user.model;

import java.sql.Date;

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
}
