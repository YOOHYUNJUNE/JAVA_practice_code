package com.kosta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final JwtProperties jwtProperties;

	// JWT Provider
	@Bean
	JwtProvider jwtProvider() {
		return new JwtProvider(userDetailsService, jwtProperties);
	}
	
	// 암호화 빈
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	// HTTP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		// 경로 권한 설정
		http.authorizeHttpRequests(auth -> 
			// 인증 필요없이 접근 가능한 특정 URL
			auth.requestMatchers(
				new AntPathRequestMatcher("/api/auth/login"), // 로그인
				new AntPathRequestMatcher("/api/auth/signup"), // 회원가입
				new AntPathRequestMatcher("/api/auth/duplicate") // 이메일 중복체크
			).permitAll()
			// 인증(ADMIN) 필요한 나머지 URL
			.requestMatchers(
				new AntPathRequestMatcher("/api/auth") // ADMIN만 가능
			).hasRole("ADMIN")
			// 그외 URL은 로그인(ROLE_USER) 유저라면 가능
			.anyRequest().authenticated()			
		);
		
		
		// 무상태성 세션 관리
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// 토큰을 통해 검증할 수 있도록, 필터 추가
		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class);
		// HTTP 기본 설정
		http.httpBasic(HttpBasicConfigurer::disable);
		// CSRF 비활성화
		http.csrf(AbstractHttpConfigurer::disable);
		// CORS 비활성화 (나중에 변경)
		http.cors(AbstractHttpConfigurer::disable);
				
		return http.getOrBuild();
		
	}
	
	
	
}
