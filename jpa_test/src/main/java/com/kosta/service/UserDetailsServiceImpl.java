package com.kosta.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository ur;
	
	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = ur.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));
		return user;
	}

}
