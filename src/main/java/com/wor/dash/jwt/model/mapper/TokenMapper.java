package com.wor.dash.jwt.model.mapper;

import java.util.List;

import com.wor.dash.jwt.model.Token;
import com.wor.dash.jwt.model.TokenSelective;

public interface TokenMapper {

	List<Token> selectAllAccessTokensByUser(Integer userId);
	Token selectByAccessToken(String token);
	Token selectByRefreshToken(String cond);
	int insertSelective(Token token);
	int updateBySelective(TokenSelective tokenSelective);
	int updateLoginStatus(String accessToken);
	int updateLogoutStatus(String accessToken);

}
