package com.kosta.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsService uds;
	
	// HTTP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth -> auth.requestMatchers(
				// 인증, 인가 설정 : 특정 URL 엑세스
				new AntPathRequestMatcher("/login"),
				new AntPathRequestMatcher("/join"),
				new AntPathRequestMatcher("/static/**/")
				).permitAll()
				//나머지 URL은 인증 필요
				.anyRequest().authenticated()
				// form 기반 로그인 설정: login.html에서 성공시 blog.list
				).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/list"))
				// 로그아웃시 login으로 + 세션 만료
				.logout(logout -> logout.logoutSuccessUrl("/login").invalidateHttpSession(true))
				// CSRF 공격 방지 설정
				.csrf(AbstractHttpConfigurer::disable)
				// CORS 비활성화
				.cors(AbstractHttpConfigurer::disable)
				.build();
		
	}
	
	
	// 인증 관리자 설정
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCrypt) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(uds);
		authProvider.setPasswordEncoder(bCrypt);
		return new ProviderManager(authProvider);			
	}
	
	
	// 비밀번호 암호화
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
}
