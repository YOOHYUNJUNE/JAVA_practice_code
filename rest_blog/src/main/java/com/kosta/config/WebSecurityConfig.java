package com.kosta.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.kosta.repository.UserRepository;
import com.kosta.security.JwtAuthenticationFilter;
import com.kosta.security.JwtAuthenticationService;
import com.kosta.security.JwtProperties;
import com.kosta.security.JwtProvider;
import com.kosta.security.LoginCustomAuthenticationFilter;
import com.kosta.util.TokenUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final JwtProperties jwtProperties;
	private final UserRepository userRepository;

	// JWT Provider
	private JwtProvider jwtProvider() {
		return new JwtProvider(userDetailsService, jwtProperties);
	}
	
	private TokenUtils tokenUtils() {
		return new TokenUtils(jwtProvider());
	}
	
	private JwtAuthenticationService jwtAuthenticationService() {
		return new JwtAuthenticationService(tokenUtils(), userRepository);
	}
	
	
	// 인증 관리자 설정(AuthenticationManager)
	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return new ProviderManager(authProvider);
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
				new AntPathRequestMatcher("/img/**"), // 이미지
				new AntPathRequestMatcher("/api/auth/signup"), // 회원가입
				new AntPathRequestMatcher("/api/auth/duplicate"), // 이메일 중복체크
				new AntPathRequestMatcher("/api/auth/refresh-token"), // 토큰 재발급
				new AntPathRequestMatcher("/api/post/**", "GET") // 게시물 보기
			).permitAll()
			// 인증(ADMIN) 필요한 나머지 URL
			.requestMatchers(
				new AntPathRequestMatcher("/api/auth/**") // ADMIN만 가능
			).hasRole("ADMIN")
			// 그외 URL은 로그인(ROLE_USER) 유저라면 가능
			.anyRequest().authenticated()			
		);
		
		
		// 무상태성 세션 관리
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// 특정 경로(Login)에 대한 필터
		http.addFilterBefore(new LoginCustomAuthenticationFilter(authenticationManager(), jwtAuthenticationService()), UsernamePasswordAuthenticationFilter.class);
		// 토큰을 통해 검증할 수 있는 필터 추가
		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class);
		// HTTP 기본 설정
		http.httpBasic(HttpBasicConfigurer::disable);
		// CSRF 비활성화
		http.csrf(AbstractHttpConfigurer::disable);
		// CORS 설정
		http.cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()));
				
		return http.getOrBuild();		
	}
	
	
	// CORS 설정
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
			config.setAllowCredentials(true);
			return config;
		};
	}
	
	
	
	
}
