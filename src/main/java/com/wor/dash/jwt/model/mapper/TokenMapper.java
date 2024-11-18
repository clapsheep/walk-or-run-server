package com.wor.dash.jwt.model.mapper;

import java.util.List;

import com.wor.dash.jwt.model.Token;

public interface TokenMapper {
	
	List<Token> findAllAccessTokensByUser(Integer userId);
	
	
	Token selectByAccessToken(String token);
	
	
	Token selectByRefreshToken(String cond);
	
	
	int insertSelective(Token token);
	
	
	int updateBySelective(Token token, String cond);
}
