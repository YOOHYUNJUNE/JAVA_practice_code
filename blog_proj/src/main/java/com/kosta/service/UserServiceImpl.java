package com.kosta.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	// 암호화를 위해 Bean가져오기
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public Long save(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword); // 유저 ID가져오기 전에 암호화
		return userRepository.save(user).getId(); // Long타입으로 불렀으므로 getId()
	}

	
	
	
	
	
}
