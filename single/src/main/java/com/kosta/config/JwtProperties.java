package com.kosta.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Component
@ConfigurationProperties("jwt") // yml에 설정한 issuer 등 매핑
public class JwtProperties {

	private String issuer;
	private String secretKey;
	private int accessDuration;
	private int refreshDuration;
	
}
