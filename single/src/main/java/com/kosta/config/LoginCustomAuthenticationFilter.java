package com.kosta.config;

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

	private static final AntPathRequestMatcher LOGIN_PATH = new AntPathRequestMatcher("/api/userlogin", "POST");
	private JwtAuthenticationService jwtAuthenticationService;
	
	
	public LoginCustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtAuthenticationService jwtAuthenticationService) {
		super(LOGIN_PATH);
		setAuthenticationManager(authenticationManager);
		this.jwtAuthenticationService = jwtAuthenticationService;
		
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// POST 방식으로 /api/login 요청이 오면 진행
		LoginRequest loginRequest = null;
		
		// 1. body에 있는 로그인정보 ({ "email" : "@@", "password" : "@@" })를 가져오게 함
		try {
			log.info("[LoginCustomAuthenticationFilter > attemptAuthentication] 로그인 정보 가져오기");
			ObjectMapper objectMapper = new ObjectMapper();
			loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);			
		} catch (Exception e) {
			throw new RuntimeException("[로그인 불가] 로그인 요청 파라미터 이름 확인 필요");
		}
		
		// 2. 이메일, 비밀번호를 기반으로 토큰 생성
		log.info("[LoginCustomAuthenticationFilter > attemptAuthentication] 토큰 생성");
		UsernamePasswordAuthenticationToken uPAT = 
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
		
		// 3. 인증 시작(manager의 authenticate 메소드 -> loadUserByUsername)
		log.info("[LoginCustomAuthenticationFilter > attemptAuthentication] 인증 시작");
		Authentication authenticate = getAuthenticationManager().authenticate(uPAT);
		
		return authenticate;
	}
	
	
	// 로그인 성공시 실행 메소드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("로그인 성공 -> 토큰 생성");
		jwtAuthenticationService.successAuthentication(response, authResult);
	}
	
	
	
}
