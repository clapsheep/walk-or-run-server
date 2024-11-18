package com.wor.dash.user.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int userId;
	private String userPassword;
	private String userName;
	private String userEmail;
	private String userNickName;
	private String userPhoneNumber;
	private Date userJoinDate;
	private Date userWithdrawalDate;
	private int userWithdrawalStatus;
	private String userRole;
	public int getUserId() {
		return userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public Date getUserJoinDate() {
		return userJoinDate;
	}
	public Date getUserWithdrawlDate() {
		return userWithdrawalDate;
	}
	public int getUserWithdrawlStatus() {
		return userWithdrawalStatus;
	}
	public String getUserRole() {
		return userRole;
	}
	
	
}
