package com.wor.dash.user.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
	private String userNickname;
	private String userPhoneNumber;
	private String userJoinDate;
	private String userWithdrawalDate;
	private int userWithdrawalStatus;
	private String userRole;
	
	public User(String userEmail, String userPassword) {
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userName = "";
		this.userNickname = "";
		this.userPhoneNumber = "";
	}
	
	public User(String userEmail, String userPassword, String userName, 
			String userNickname, String userPhoneNumber) {
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userNickname = userNickname;
		this.userPhoneNumber = userPhoneNumber;
	}
	
//	public User()
	
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
	public String getUserNickname() {
		return userNickname;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public String getUserJoinDate() {
		return userJoinDate;
	}
	public String getUserWithdrawlDate() {
		return userWithdrawalDate;
	}
	public int getUserWithdrawlStatus() {
		return userWithdrawalStatus;
	}
	public String getUserRole() {
		return userRole;
	}
	
	
}
