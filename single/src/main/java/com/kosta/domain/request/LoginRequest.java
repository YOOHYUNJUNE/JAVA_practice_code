package com.kosta.domain.request;

import lombok.Data;

// 로그인 요청 : 이메일, 비밀번호
@Data
public class LoginRequest {

	private String email;
	private String password;
	
}
