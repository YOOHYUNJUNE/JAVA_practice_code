package com.kosta.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

	@Override
	public UserResponse signUp(SignUpRequest signUpRequest) {
		User user = User.builder()
				.email(signUpRequest.getEmail())
				.name(signUpRequest.getName())
				.password(signUpRequest.getPassword())
				.build();
		User savedUser = userRepository.save(user);
		return UserResponse.toDTO(savedUser);
	}
	
	

	@Override
	public List<UserResponse> getUserList() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(UserResponse::toDTO).toList();
	}

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
		return userRepository.existsByEmail(email);
	}
}