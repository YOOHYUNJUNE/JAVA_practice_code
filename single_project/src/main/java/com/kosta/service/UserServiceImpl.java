package com.kosta.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.domain.UserDTO;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void join(UserDTO userDTO, String ip) {
		String password = userDTO.getPassword();
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		userDTO.setPassword(encodedPassword);
		User user = userDTO.setUser();
		user.setIp(ip);
		userRepository.save(user);
	}
}
