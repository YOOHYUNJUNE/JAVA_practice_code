package com.kosta.damain;

import java.time.LocalDateTime;

import com.kosta.entity.Post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {

	private Long id;
	private String title, content;
	private UserResponse author;
	private LocalDateTime createdAt, updatedAt;
	
	// Post -> PostResponse 변환
	public static PostResponse toDTO(Post post) {
		return PostResponse.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.author(UserResponse.toDTO(post.getAuthor())) // post.getAuthor() : User 타입 / author : UserResponse 타입
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.build();
	}
	
}
