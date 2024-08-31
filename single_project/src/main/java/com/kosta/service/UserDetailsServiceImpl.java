package com.kosta.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
	private final UserRepository userRepository;
	
	@Override
	public User loadUserByUsername(String username) {
		Optional<User> userOpt = userRepository.findByUsername(username);
		User user = userOpt.orElseThrow(()-> new IllegalArgumentException(username + " 없음"));
		return user;
	}
}
