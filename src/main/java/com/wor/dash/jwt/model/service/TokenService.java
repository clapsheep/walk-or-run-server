package com.wor.dash.jwt.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.jwt.model.Token;

public interface TokenService {

	List<Token> findAllAccessTokensByUser(Integer userId);
    Optional<Token> findByAccessToken(String token);
    Optional<Token> findByRefreshToken(String token);
	void addToken(Token token);
	void addAllTokens(List<Token> validTokens);
	void updatgDsave(Token storedToken);
	void updateLogin(String accessToken);
	void updateLogout(String accessToken);

}
