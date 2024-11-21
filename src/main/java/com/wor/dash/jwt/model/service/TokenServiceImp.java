package com.wor.dash.jwt.model.service;

import java.util.List;
import java.util.Optional;

import com.wor.dash.jwt.model.TokenSelective;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wor.dash.jwt.model.Token;
import com.wor.dash.jwt.model.mapper.TokenMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class TokenServiceImp implements TokenService {

	private final TokenMapper tokenMapper;

/*
select t from Token t inner join User u on t.user.id = u.id
where t.user.id = :userId and t.loggedOut = false
 */

	@Override
	public List<Token> findAllAccessTokensByUser(Integer userId) {
		return tokenMapper.selectAllAccessTokensByUser(userId);
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
		if(validTokens != null && !validTokens.isEmpty()) {
			for(int i=0; i < validTokens.size(); i++) {
				TokenSelective tokenSelective = new TokenSelective();
				tokenSelective.setCond(validTokens.get(i).getTokenId());
				tokenSelective.setLogout(1);
				tokenMapper.updateBySelective(tokenSelective);
			}
		}
	}

	@Override
	public void updatgDsave(Token storedToken) {
		TokenSelective tokenSelective = new TokenSelective();
		tokenSelective.setTokenId(storedToken.getTokenId());
		tokenSelective.setLogout(1);
		tokenSelective.setCond(storedToken.getTokenId());
		tokenMapper.updateBySelective(tokenSelective);
	}

	@Override
	public void updateLogin(String accessToken) {
		tokenMapper.updateLoginStatus(accessToken);
	}

	@Override
	public void updateLogout(String accessToken) {
		tokenMapper.updateLogoutStatus(accessToken);
	}

}