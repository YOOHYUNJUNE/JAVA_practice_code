package com.kosta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.domain.request.SignUpRequest;
import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.UserResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.security.JwtProvider;
import com.kosta.service.AuthService;
import com.kosta.util.TokenUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtProvider jwtProvider;
	private final TokenUtils tokenUtils;

	
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

	
	private String extractRefreshTokenFromCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("refreshToken")) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	// 리프레시 토큰 요청
	@Override
	public Map<String, String> refreshToken(HttpServletRequest req) {
		// 쿠키에서 RefreshToken 호출
		String refreshToken = extractRefreshTokenFromCookie(req);
		log.info("리프레시 토큰 : {}", refreshToken);
		// 토큰이 유효하지 않으면 null
		if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
			return null;
		}
		// 유효한 토큰에서 이메일 추출
		String userEmail = jwtProvider.getUserEmailByToken(refreshToken);
		log.info("추출한 이메일: {}",userEmail);
		
		// 이메일로 사용자 조회후, 리프레시 토큰 비교
		User user = userRepository.findByEmail(userEmail).orElse(null);
		if (user == null || !user.getRefreshToken().equals(refreshToken)) {
			return null;
		}
		// 새 토큰 생성 후 DB에 리프레시 토큰 저장
		Map<String, String> tokenMap = tokenUtils.generateToken(user);		
		user.setRefreshToken(tokenMap.get("refreshToken"));
		userRepository.save(user);
		
		return tokenMap;


	}



	
	
}