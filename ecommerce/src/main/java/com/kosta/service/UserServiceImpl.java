package com.kosta.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.domain.UserDTO;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository ur;
	private final BCryptPasswordEncoder bc;

	@Override
	public boolean islogin() {
		return false;
	}


	// UserDTO를 만들어서 유저 정보 일부 컬럼만 가져오기
	@Override
	public void join(UserDTO userDTO) {
		String password = userDTO.getPassword();
		String encodedPassword = bc.encode(password);
		userDTO.setPassword(encodedPassword);;
		ur.save(userDTO.setUser());
	}


	


}
