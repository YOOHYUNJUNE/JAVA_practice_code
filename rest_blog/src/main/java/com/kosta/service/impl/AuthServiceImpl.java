package com.kosta.service.impl;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.domain.request.SignUpRequest;
import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.LoginResponse;
import com.kosta.domain.response.UserResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.security.JwtProvider;
import com.kosta.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	
	// 회원가입
	@Override
	public UserResponse signUp(SignUpRequest signUpRequest) {
		// 비밀번호 암호화
		String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
		
		User user = User.builder()
				.email(signUpRequest.getEmail())
				.name(signUpRequest.getName())
				.password(encodedPassword)
				.build();
		User savedUser = userRepository.save(user);
		return UserResponse.toDTO(savedUser);
	}
	
	
	// 유저 전체 가져오기
	@Override
	public List<UserResponse> getUserList() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(UserResponse::toDTO).toList();
	}

	
	// 회원 정보 수정(이메일로 불러옴 -> 비밀번호 확인)
	@Override
	public UserResponse updateUser(UserUpdateRequest userUpdateReqeust) {
		User user = userRepository.findByEmail(userUpdateReqeust.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("회원 정보 조회에 실패했습니다. [없는 이메일]"));

		// 사용자 입력 비번과 DB의 비번 비교
		boolean isMatch = bCryptPasswordEncoder.matches(userUpdateReqeust.getPassword(), user.getPassword());
		
		if (!isMatch) {
			throw new RuntimeException("비밀번호 입력 오류");
		}
		if (userUpdateReqeust.getName() != null)
			user.setName(userUpdateReqeust.getName());
		User updatedUser = userRepository.save(user);

		return UserResponse.toDTO(updatedUser);
	}

	
	// 회원 탈퇴(이메일로 불러옴 -> 비밀번호 확인)
	@Override
	public void deleteUser(UserDeleteRequest userDeleteRequest) {
		User user = userRepository.findByEmail(userDeleteRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("회원 정보 조회에 실패했습니다. [없는 이메일]"));
		
		// 사용자 입력 비번과 DB의 비번 비교
		boolean isMatch = bCryptPasswordEncoder.matches(userDeleteRequest.getPassword(), user.getPassword());
		
		if (!isMatch) {
			throw new RuntimeException("비밀번호 입력 오류");
		}	
		userRepository.delete(user);
	}


	// 가입시 이메일 중복 체크
	@Override
	public boolean duplicateCheckEmail(String email) {
		return !userRepository.existsByEmail(email);
	}



	
	
}