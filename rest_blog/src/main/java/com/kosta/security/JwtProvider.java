package com.kosta.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {
	
	private final UserDetailsService userDetailsService;

	// JWT 관련 설정 정보 객체 주입
	private final JwtProperties jwtProperties;
	
	
	// JWT access 토큰 생성 메소드
	public String generateAccessToken(User user) {
		log.info("[generateAccessToken] 액세스 토큰 생성");
		Date now = new Date(); // 현재 날짜 가져오기
		Date expriedDate =  new Date(now.getTime() + jwtProperties.getAccessDuration()); // 만료일
		return makeToken(user, expriedDate);
	}
	
	// JWT refresh 토큰 생성 메소드
	public String generateRefreshToken(User user) {
		log.info("[generateRefreshToken] 리프레시 토큰 생성");
		Date now = new Date(); // 현재 날짜 가져오기
		Date expriedDate =  new Date(now.getTime() + jwtProperties.getRefreshDuration()); // 만료일
		return makeToken(user, expriedDate);
	}

	
	// 토큰 생성 공통 메소드(실제 JWT 토큰 생성)
	private String makeToken(User user, Date expriedDate) {
		String token = Jwts.builder()
			.header().add("typ", "JWT") // JWT의 타입 명시
			.and()
			.issuer(jwtProperties.getIssuer()) // 발행자 정보 설정 = setIssuer
			.issuedAt(new Date()) // 발행일시 설정
			.expiration(expriedDate) // 만료일시 설정
			.subject(user.getEmail()) // 토큰의 주체(Subject) 설정 : 사용자 email
			.claim("id", user.getId())
			.claim("role", user.getRole().name())
			.signWith(getSecretKey(), Jwts.SIG.HS256) // 비밀키와 해시 알고리즘을 사용해 토큰 설명값 설정
			.compact(); // 토큰 정보를 압축해 문자열로 변환
		log.info("[makeToken] 생성된 토큰 : {}", token);

		return token;		
	}
	
	
	// 비밀키 생성 메소드
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
	}
	
	
	// 토큰 유효 검증 메소드
	public boolean validateToken(String token) {
		log.info("[validateToken] 토큰 검증 시작");
		try {
			Jwts.parser()
			.verifyWith(getSecretKey()) // 비밀키로 서명 검증
			.build()
			.parseSignedClaims(token); // 서명한 클레임 파싱
			log.info("[validateToken] 토큰 검증 통과");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("[validateToken] 토큰 검증 실패");
		return false;
	}
	
	
	// 토큰 정보(Claim) 추출 메소드
	private Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(getSecretKey())
			.build()
			.parseSignedClaims(token)
			.getPayload(); // 파싱한 클레임에서 페이로드(실제 클레임) 반환
	}
	
	
	// 토큰 인증 정보 반환 메소드
	public Authentication getAuthenticationByToken(String token) {
		log.info("[getAuthenticationByToken] 토큰 인증 정보 조회");
		Claims claims = getClaims(token);
		String userEmail = getUserEmailByToken(token);
		User user = (User) userDetailsService.loadUserByUsername(userEmail);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
			user, token, user.getAuthorities()
		);
		
		return authentication;
	}
	
	
	// 토큰에서 사용자 ID만 추출하는 메소드
	public String getUserEmailByToken(String token) {
		log.info("[getUserEmailByToken] 토큰 기반 사용자 식별 정보 추출");
		Claims claims = getClaims(token);
		String email = claims.get("sub", String.class);
		return email;
	}



	
	
	
	
	
}
