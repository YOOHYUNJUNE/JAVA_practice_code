package com.kosta.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.domain.request.LoginRequest;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


// 로그인만 하는 컨트롤러 역할
@Slf4j
public class LoginCustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final AntPathRequestMatcher LOGIN_PATH = new AntPathRequestMatcher("/api/auth/login", "POST");
	private JwtAuthenticationService jwtAuthenticationService;
	
	public LoginCustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtAuthenticationService jwtAuthenticationService) {
		super(LOGIN_PATH);
		setAuthenticationManager(authenticationManager);
		this.jwtAuthenticationService = jwtAuthenticationService;
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// POST 방식으로 /api/auth/login에 요청이 오면 진행
		LoginRequest loginRequest = null;
		
		// 1. body에 있는 로그인정보 ({ "email" : " ~~ ", "password" : " ~~ " } 를 가져오게 함)
		try {
			log.info("[attemptAuthentication] 로그인 정보 가져오기");
			ObjectMapper objectMapper = new ObjectMapper();
			loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			throw new RuntimeException("로그인 요청 파라미터 이름 확인 필요 (로그인 불가)");
		}
		
		// 2. email, password를 기반으로 토큰 생성
		log.info("[attemptAuthentication] 토큰 생성");
		UsernamePasswordAuthenticationToken uPAT = 
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()); 
		
		// 3. 인증시작 ( manager의 authenticate 메소드 동작 -> loadUserByUsername )
		log.info("[attemptAuthentication] 인증 시작");
		Authentication authenticate = getAuthenticationManager().authenticate(uPAT);
		return authenticate;
	}

	
	// 로그인 성공시 메소드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("[successfulAuthentication] 로그인 성공 > 토큰 생성");
		jwtAuthenticationService.successAuthentication(response, authResult);
	}
	
	
}
