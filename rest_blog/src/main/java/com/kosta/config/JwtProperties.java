package com.kosta.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt") // application.yml에 설정한 jwt.issuer 등을 매핑
public class JwtProperties {

	private String issuer;
	private String secretKey;
	private int accessDuration;
	
	
}
