package com.kosta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.domain.response.LoginResponse;
import com.kosta.service.OAuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthController {
	
	private final OAuthService oAuthService;

	@GetMapping("/google")
	public ResponseEntity<LoginResponse> googleSignIn(@RequestParam("code") final String code, HttpServletResponse res) {
		
		log.info("코드 값 : {}", code);
		
		String accessToken = oAuthService.googleSignIn(code, res);
		// 코드를 통해 사용자 정보를 받음
		// 사용자 정보를 조회
		// 기존 사용자일 경우 oauth값을 true로 변경
		// 없는 사용자일 경우 DB에 추가(신규가입)
		// 사용자에 대한 정보로 엑세스토큰, 리프레시토큰을 생성해서 반환
		if (accessToken == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		LoginResponse loginResponse = LoginResponse.builder().accessToken(accessToken).build();
		return ResponseEntity.ok(loginResponse);
	}
	
	
	
	
	
	
	
	
	
	
	
}
