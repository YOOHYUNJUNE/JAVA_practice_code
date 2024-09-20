package com.kosta.service.impl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.service.OAuthService;
import com.kosta.util.OAuth2Properties;
import com.kosta.util.TokenUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
	
	private final OAuth2Properties oAuth2Properties;
	private final UserRepository userRepository;
	private final TokenUtils tokenUtils;

	@Override
	public String oAuthSignIn(String code, String provider, HttpServletResponse res) {
		// 1. code로 provider로부터 엑세스 토큰을 받음
		String providedAccessToken = getAccessToken(code, provider);
		// 2. 엑세스 토큰으로 사용자 정보 추출
		User user = generateOAuthUser(providedAccessToken, provider);		
		// 3. DB에서 사용자 정보를 조회
		user = userRepository.findByEmail(user.getEmail()).orElse(user);
		if (!user.isOAuth()) {
			// 기존 사용자일 경우 oauth -> true로 변경
			user.setOAuth(true);
//			// 없는 사용자일 경우 DB에 추가(신규가입)
//			userRepository.save(user);		
		}
		// 4. 자동 로그인 (사용자에 대한 정보로 엑세스토큰, 리프레시토큰을 생성해서 반환)
		Map<String, String> tokenMap = tokenUtils.generateToken(user);
		// DB에 기록 (리프레시 토큰)
		user.setRefreshToken(tokenMap.get("refreshToken"));
		userRepository.save(user);			
		// HEADER에 추가 (리프레시 토큰)
		tokenUtils.setRefreshTokenCookie(res, tokenMap.get("refreshToken"));
		// BODY에 추가 (엑세스 토큰)
		return tokenMap.get("accessToken");
	}

	// 엑세스 토큰 가져오는 메소드
	private String getAccessToken(String code, String provider) {
		// 설정 가져오기
		OAuth2Properties.Client client = oAuth2Properties.getClients().get(provider);
		
		// 1. code로 provider로부터 엑세스 토큰을 받음
		String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		headers.setBasicAuth(client.getClientId(), client.getClientSecret());
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("client_id", client.getClientId());
		params.add("client_secret", client.getClientSecret());
		params.add("code", decodedCode);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", client.getRedirectUri());
		
		// 엑세스 토큰 받아오기
		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<Map> responseEntity = rt.postForEntity(client.getTokenUri(), requestEntity, Map.class);
		
		if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자의 정보를 가져올 수 없습니다.");
		}
		
		return (String) responseEntity.getBody().get("access_token");
	}
	
	

	// 사용자 정보 추출하는 메소드
	private User generateOAuthUser(String accessToken, String provider) {
		// 설정 가져오기
		OAuth2Properties.Client client = oAuth2Properties.getClients().get(provider);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<JsonNode> responseEntity = rt.exchange(client.getUserInfoRequestUri(), HttpMethod.GET, new HttpEntity<>(headers), JsonNode.class);
		
		if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자의 정보를 가져올 수 없습니다.");
		}
		
		// 유저 정보
		JsonNode jsonNode = responseEntity.getBody();
//		System.out.println(jsonNode);
		// 유저 정보 초기화
		String email = null;
		String name = null;
		User user = null;
		
		try {
			// 이름과 이메일이 존재할 경우 user 객체 생성
			if (jsonNode.has("email") && jsonNode.has("name")) {
				email = jsonNode.get("email").asText();
				name = jsonNode.get("name").asText();				
			
			// 카카오에서 email을 제공하지 않아서 쓰는 임시 코드
			} else if (jsonNode.has("id") && jsonNode.has("properties")) {
				email = jsonNode.get("id").asText() + "@kakao.com";
				name = jsonNode.get("properties").get("nickname").asText();
			}
			user = User.builder()
					.email(email)
					.name(name)
					.build();	
			
		} catch (RuntimeException e) {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
		
		return user;
	}
	
	
	
	
	
	
	
}
