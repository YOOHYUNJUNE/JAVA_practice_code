package com.kosta.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository ur;
	
	@Override
	public User loadUserByUsername(String email) {
		Optional<User> user = ur.findByEmail(email);
		return user.orElseThrow(() -> new IllegalArgumentException(email + " 없음"));
	}
	
	
	
}
