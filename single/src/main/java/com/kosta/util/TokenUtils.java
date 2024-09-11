package com.kosta.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.config.JwtProvider;
import com.kosta.domain.response.LoginResponse;
import com.kosta.entity.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 토큰 생성, 관리 클래스
@Component
@RequiredArgsConstructor
public class TokenUtils {

	private final JwtProvider jwtProvider;
	
	
	// 토큰 생성
	public Map<String, String> generateToken(User user) {
		
		String accessToken = jwtProvider.generateAccessToken(user);
		String refreshToken = jwtProvider.generateRefreshToken(user);
		
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("accessToken", accessToken);
		tokenMap.put("refreshToken", refreshToken);
		
		return tokenMap;		
	}
	
	
	// JSON 응답 전송
	public void writeResponse(HttpServletResponse res, LoginResponse loginResponse) throws IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(loginResponse);
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(jsonResponse);
	}
	
	
	// 리프레시 토큰 생성해 쿠키로 보내기
	public void setRefreshTokenCookie(HttpServletResponse res, String refreshToken) {
		// 쿠키에서 수정 불가
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true); // js에서 변경 불가
		refreshTokenCookie.setSecure(false); // http가 아니어도 사용 가능
		refreshTokenCookie.setPath("/"); // 토큰 사용 경로
		refreshTokenCookie.setMaxAge(1 * 24 * 60 * 60); // 유효기간:1일
		res.addCookie(refreshTokenCookie);
	}
	
	
}
