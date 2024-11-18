package com.wor.dash.jwt.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wor.dash.jwt.model.Token;

@Mapper
public interface TokenMapper {
	
	List<Token> findAllAccessTokensByUser(Integer userId);
	
	
	Token selectByAccessToken(String token);
	
	
	Token selectByRefreshToken(String cond);
	
	
	int insertSelective(Token token);
	
	
	int updateBySelective(Token token, String cond);
}
