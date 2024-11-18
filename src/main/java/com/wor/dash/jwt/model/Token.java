package com.wor.dash.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private Integer tokenId;
    private int userId;
    private String accessToken;
    private String refreshToken;
    private int logout;
    
//	public void setTokenId(Integer tokenId) {
//		this.tokenId = tokenId;
//	}
//	public void setAccessToken(String accessToken) {
//		this.accessToken = accessToken;
//	}
//	public void setLoggedOut(int logout) {
//		this.logout = logout;
//	}
//	public void setRefreshToken(String refreshToken) {
//		this.refreshToken = refreshToken;
//	}
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
    
    
}
