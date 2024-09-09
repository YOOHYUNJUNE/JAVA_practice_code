package com.kosta.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.config.JwtProvider;
import com.kosta.domain.LoginResponse;
import com.kosta.domain.SignUpRequest;
import com.kosta.domain.UserDeleteRequest;
import com.kosta.domain.UserResponse;
import com.kosta.domain.UserUpdateRequest;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtProvider jwtProvider;

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

		if (!user.getPassword().equals(userUpdateReqeust.getPassword())) {
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
		if (!user.getPassword().equals(userDeleteRequest.getPassword())) {
			throw new RuntimeException("비밀번호 입력 오류");
		}	
		userRepository.delete(user);
	}


	// 가입시 이메일 중복 체크
	@Override
	public boolean duplicateCheckEmail(String email) {
		return !userRepository.existsByEmail(email);
	}


	// 로그인 기능
	@Override
	public LoginResponse login(String email, String password) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다."));
		// 평문, 암호문 일치여부 판단
		boolean matchedPassword = bCryptPasswordEncoder.matches(password, user.getPassword()); 
		if (!matchedPassword) throw new RuntimeException("비밀번호 불일치");
		String accessToken = jwtProvider.generateAccessToken(user);
		
		return LoginResponse.builder().accessToken(accessToken).build();
	}
	
	
}