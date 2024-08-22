package com.kosta.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository ur;
	
	// 암호화하기 위해 Bean 가져오기
	private final BCryptPasswordEncoder bc;
	
	@Override
	public Long save(User user) {
		String encodedPw = bc.encode(user.getPassword());
		user.setPassword(encodedPw);
		return ur.save(user).getId();
	}

}
