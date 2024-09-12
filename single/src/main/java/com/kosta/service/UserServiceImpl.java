package com.kosta.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.config.JwtProvider;
import com.kosta.domain.request.JoinRequest;
import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.UserResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.util.TokenUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtProvider jwtProvider;
	private final TokenUtils tokenUtils;
	
	// 회원가입
	@Override
	public UserResponse join(JoinRequest joinRequest) {
		// 비밀번호 암호화
		String encodedPassword = bCryptPasswordEncoder.encode(joinRequest.getPassword());
		
		User user = User.builder()
				.email(joinRequest.getEmail())
				.name(joinRequest.getName())
				.password(encodedPassword)
				.build();
		User savedUser = userRepository.save(user);
		
		return UserResponse.toDTO(savedUser);
	}
	
	// 회원가입시 이메일 중복 체크
	@Override
	public boolean duplicateCheckEmail(String email) {
		return !userRepository.existsByEmail(email);
	}

	// 리프레시 토큰 요청
	@Override
	public Map<String, String> refreshToken(HttpServletRequest req) {
		// 쿠키에서 리프레시 토큰 호출
		String refreshToken = extractRefreshTokenFromCookie(req);
		log.info("리프레시 토큰 : {}", refreshToken);
		// 토큰이 유효하지 않으면 null
		if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
			return null;
		}
		// 유효한 토큰에서 이메일 추출
		String userEmail = jwtProvider.getUserEmailByToken(refreshToken);
		log.info("추출한 이메일 : {}", userEmail);
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
	// 쿠키로부터 리프레시 토큰 추출
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

	
	// 유저 전체 목록
	@Override
	public List<UserResponse> getUserList() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(UserResponse::toDTO).toList();
	}

	
	// 유저 수정(이메일로 불러오고 비밀번호 확인 후 변경)
	@Override
	public UserResponse updateUser(UserUpdateRequest userUpdateRequest) {
		User user = userRepository.findByEmail(userUpdateRequest.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));
		// matches(입력한 비밀번호, DB의 비밀번호) 비교
		boolean isMatch = bCryptPasswordEncoder.matches(userUpdateRequest.getPassword(), user.getPassword());
		// matches 오류 시 현재 메소드의 실행이 중단
		if (!isMatch) throw new RuntimeException("비밀번호 입력 오류입니다.");
		// 이름 변경을 하지 않으면 업데이트 되지 않음
		if (userUpdateRequest.getName() != null) user.setName(userUpdateRequest.getName());
		// 비밀 번호 변경 -> 이메일, 휴대폰 등으로 실제 인증을 거친 후에 해야함
		User updateUser = userRepository.save(user);
		
		return UserResponse.toDTO(updateUser);
	}

	
	// 회원 탈퇴
	@Override
	public void deleteUser(UserDeleteRequest userDeleteRequest) {
		User user = userRepository.findByEmail(userDeleteRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));
		// 입력 비번, DB 비번 비교
		boolean isMatch = bCryptPasswordEncoder.matches(userDeleteRequest.getPassword(), user.getPassword());
		// matches 오류 시 현재 메소드의 실행이 중단
		if (!isMatch) throw new RuntimeException("비밀번호 입력 오류입니다.");
		
		userRepository.delete(user);
	}

	
	
	
}
