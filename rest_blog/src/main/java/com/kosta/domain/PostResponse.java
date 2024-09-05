package com.kosta.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kosta.entity.Post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {

	private Long id;
	private String title, content;
	private UserResponse author;
	private String createdAt, updatedAt;
	private FileDTO image;
	
	// Post -> PostResponse 변환
	public static PostResponse toDTO(Post post) {
		return PostResponse.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.author(UserResponse.toDTO(post.getAuthor())) // post.getAuthor() : User 타입 / author : UserResponse 타입
			.createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.updatedAt(post.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.image(FileDTO.toDTO(post.getImage()))
			.build();
	}
	
}
