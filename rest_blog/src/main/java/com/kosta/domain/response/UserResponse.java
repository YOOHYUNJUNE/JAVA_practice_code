package com.kosta.domain.response;

import java.time.format.DateTimeFormatter;

import com.kosta.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private Long id;
	private String email, name, createdAt;
	
	// User -> UserResponse 변환
	public static UserResponse toDTO(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
			.build();
	}
	
}
