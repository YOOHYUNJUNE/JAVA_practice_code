package com.kosta.service;

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

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
	
	private final UserRepository userRepository;

	@Override
	public String googleSignIn(String code, HttpServletResponse res) {
		// 1. code로 구글로부터 엑세스 토큰을 받음
		String providedAccessToken = getAccessToken(code);
		// 2. 엑세스 토큰으로 사용자 정보 추출
		User user = generateOAuthUser(providedAccessToken);		
		// 3. DB에서 사용자 정보를 조회
		user = userRepository.findByEmail(user.getEmail()).orElse(user);
			// 기존 사용자일 경우 oauth값을 true로 변경
		user.setOAuth(true);
			// 없는 사용자일 경우 DB에 추가(신규가입)
		userRepository.save(user);
		// 사용자에 대한 정보로 엑세스토큰, 리프레시토큰을 생성해서 반환
		return null;
	}

	// 엑세스 토큰 가져오는 메소드
	private String getAccessToken(String code) {
		// 1. code로 구글로부터 엑세스 토큰을 받음
		String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		String ClientId = "1081047189641-c86rm2u735o2difi0a3im99v1q01lch3.apps.googleusercontent.com";
		String ClientSecret = "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";
		String redirectURI = "http://localhost:3000/oauth/google";
		headers.setBasicAuth(ClientId, ClientSecret);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("client_id", ClientId);
		params.add("client_secret", ClientSecret);
		params.add("code", decodedCode);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", redirectURI);
		
		// 엑세스 토큰 받아오기
		String tokenURI = "https://oauth2.googleapis.com/token";
		
		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<Map> responseEntity = rt.postForEntity(tokenURI, requestEntity, Map.class);
		
		if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자의 정보를 가져올 수 없습니다.");
		}
		
		return (String) responseEntity.getBody().get("access_token");
	}

	// 사용자 정보 추출하는 메소드
	private User generateOAuthUser(String accessToken) {
		String userInfoURI = "https://www.googleapis.com/oauth2/v3/userinfo";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<JsonNode> responseEntity = rt.exchange(userInfoURI, HttpMethod.GET, new HttpEntity<>(headers), JsonNode.class);
		
		if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자의 정보를 가져올 수 없습니다.");
		}
		
		// 유저 정보
		JsonNode jsonNode = responseEntity.getBody();
		// 유저 정보 초기화
		String email = null;
		String name = null;
		User user = null;
		
		try {
			// 이름과 이메일이 존재할 경우 user 객체 생성
			if (jsonNode.has("email") && jsonNode.has("name")) {
				email = jsonNode.get("email").asText();
				name = jsonNode.get("name").asText();				
				user = User.builder()
						.email(email)
						.name(name)
//						.oAuth(true)
						.build();				
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
		
		return user;
	}
	
	
	
	
	
	
	
}
