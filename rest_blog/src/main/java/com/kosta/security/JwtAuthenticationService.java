package com.kosta.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kosta.domain.response.LoginResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.util.TokenUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


// 로그인 인증 관련 서비스
@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
	
	private final TokenUtils tokenUtils;
	private final UserRepository userRepository;
	
	// 로그인 성공
	void successAuthentication(HttpServletResponse response, Authentication authResult) throws IOException {
		User user = (User) authResult.getPrincipal(); // 로그인 성공한 User 가져오기
		Map<String, String> tokenMap = tokenUtils.generateToken(user);
		String accessToken = tokenMap.get("accessToken"); // 액세스 토큰
		String refreshToken = tokenMap.get("refreshToken"); // 리프레시 토큰 -> 쿠키로 보내서 수정 불가하게 하기
		
		// refresh 토큰을 DB에 저장
		user.setRefreshToken(refreshToken);
		userRepository.save(user);
		
		// 생성된 refresh 토큰을 쿠기에 담기
		tokenUtils.setRefreshTokenCookie(response, refreshToken);
		
		// 생성된 access 토큰을 LoginResponse에 담아 전달
		LoginResponse loginResponse = LoginResponse.builder().accessToken(accessToken).build();
		tokenUtils.writeResponse(response, loginResponse);
		
	}
	

}
