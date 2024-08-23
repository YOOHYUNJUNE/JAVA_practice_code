package com.kosta.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	
	// 비밀번호 암호화 후 DB에 저장
	@Override
	public Long save(User user) {
		String encodedPw = bc.encode(user.getPassword());
		user.setPassword(encodedPw);
		return ur.save(user).getId();
	}

	
	// 로그인 여부
	@Override
	public boolean islogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth instanceof AnonymousAuthenticationToken) return false; 
		return auth.isAuthenticated();
	}

	
	
	
	
	
}
