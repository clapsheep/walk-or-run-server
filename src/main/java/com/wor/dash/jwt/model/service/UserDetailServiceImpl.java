package com.wor.dash.jwt.model.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wor.dash.user.model.CustomUserDetails;
import com.wor.dash.user.model.User;
import com.wor.dash.user.model.service.UserService;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
private UserService userService;
	
	public UserDetailServiceImpl(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
		Optional<User> nUser = userService.getUser(useremail); //조인으로 수정
		return nUser.map(CustomUserDetails::new)
		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
