package com.kosta.config;

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
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kosta.common.LoginSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final UserDetailsService ud;
	private final LoginSuccessHandler ls;
	
	
	// HTTP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth ->
		auth
		.requestMatchers(
				// 인증, 인가 설정 (특정 URL 액세스 설정)
				new AntPathRequestMatcher("/"),
				new AntPathRequestMatcher("/join"),
				new AntPathRequestMatcher("/login"),
				new AntPathRequestMatcher("/index"),
				new AntPathRequestMatcher("/logout"),
				
				new AntPathRequestMatcher("/err"),
				new AntPathRequestMatcher("/list/**"),
				new AntPathRequestMatcher("/detail/**"),
				new AntPathRequestMatcher("/admin/**")
				).permitAll()
//				.requestMatchers(
//						new AntPathRequestMatcher("/admin/**")
//						).hasRole("ADMIN")
//						// 나머지 URL은 인증 필요
						.anyRequest().authenticated()				
				);
		
		// 폼 기반 로그인 설정(로그인(login) 성공시 index 이동)
		http.formLogin(form -> form.loginPage("/").defaultSuccessUrl("/").loginProcessingUrl("/login"));
		// 로그아웃 설정(로그아웃 시 index 연결, 동시에 세션 만료)
		http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true));
		
//		// continue 파라미터를 null로 설정
//		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
//		requestCache.setMatchingRequestParameterName(null);
//		http.requestCache((cache) -> cache
//				.requestCache(requestCache)
//				);
		
		// CSRF 공격 방지 설정
		http.csrf(AbstractHttpConfigurer::disable);
		// CORS 비활성화
		http.cors(AbstractHttpConfigurer::disable);
		
		return http.getOrBuild();				
	}
	
// 인증 관리자 설정
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCrypt, UserDetailsService uds) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(ud);
		authProvider.setPasswordEncoder(bCrypt);
		return new ProviderManager(authProvider);
	}
	
// 비밀번호 암호활르 위한 사용 설정
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}
