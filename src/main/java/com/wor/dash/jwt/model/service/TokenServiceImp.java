package com.wor.dash.jwt.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wor.dash.jwt.model.Token;
import com.wor.dash.jwt.model.dao.TokenMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenServiceImp implements TokenService {

	private TokenMapper tokenMapper;
	
	public TokenServiceImp(TokenMapper tokenMapper) {
		super();
		this.tokenMapper = tokenMapper;
	}
/*
select t from Token t inner join User u on t.user.id = u.id
where t.user.id = :userId and t.loggedOut = false
 */

	@Override
	public List<Token> findAllAccessTokensByUser(Integer userId) {
		return tokenMapper.findAllAccessTokensByUser(userId);
	}

	@Override
	public Optional<Token> findByAccessToken(String token) {
		Token accessToken = tokenMapper.selectByAccessToken(token);
		if(accessToken != null) {
			return Optional.ofNullable(accessToken);
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public Optional<Token> findByRefreshToken(String token) {
		Token accessToken = tokenMapper.selectByRefreshToken(token);
		if(accessToken != null) {
			return Optional.ofNullable(accessToken);
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public void addToken(Token token) {
		token.setTokenId(null);
		log.debug("TokenServiceImpl/addToken=============================={}111", token);
		tokenMapper.insertSelective(token);
		log.debug("TokenServiceImpl/addToken=============================={}222", token);
	}

	/**
	 * Example class 대신 String으로 조건 넣어줌 mapper.xml 만들 때 참고
	 */
	@Override
	@Transactional
	public void addAllTokens(List<Token> validTokens) {
		if(validTokens != null && validTokens.size() > 0) {
			for(int i=0; i < validTokens.size(); i++) {
				String cond = "id = " + validTokens.get(i).getTokenId();
				Token token = new Token();
				token.setLogout(1);
				tokenMapper.updateBySelective(token, cond);
			}
		}
	}

	@Override
	public void updatgDsave(Token storedToken) {
		Token token = new Token();
		token.setTokenId(storedToken.getTokenId());
		token.setLogout(1);
		String cond = "id = " + storedToken.getTokenId();
		tokenMapper.updateBySelective(token, cond);
	}

}