package com.kosta.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService;
	
//	// 특정 부분에 스프링 시큐리티 비활성화
//	@Bean
//	WebSecurityCustomizer configure() {
//		return (web) -> web.ignoring().requestMatchers(
//						new AntPathRequestMatcher("/static/**/")
//						);
//	}
	
	
	// HTTP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth -> 
			auth.requestMatchers(
				// 인증, 인가 설정 : 특정 URL 엑세스 설정
				new AntPathRequestMatcher("/login"),
				new AntPathRequestMatcher("/join"),
				new AntPathRequestMatcher("/static/**/")			

			).permitAll()
				// 나머지 URL은 인증 필요
			.anyRequest().authenticated()
			// form 기반 로그인 설정 : 로그인은 login.html로 이동, 성공 시 "blog/list"로 연결
		).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/blog/list"))
		// 로그아웃 성공 시 "/login"으로 연결 + 세션 만료
		.logout(logout -> logout.logoutSuccessUrl("/login").invalidateHttpSession(true))
		// CSRF 공격 방지 설정
		.csrf(AbstractHttpConfigurer::disable)
		// CORS 비활성화
		.cors(AbstractHttpConfigurer::disable)
		.build();
	}
	
	
	// 인증 관리자 설정 (매개변수 uds 사용X)
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCrypt, UserDetailsService uds) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(bCrypt);		
		return new ProviderManager(authProvider);
	}
	
	
	// 비밀번호 암호화를 위한 사용 설정
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	
	
	
	
	
	
}
