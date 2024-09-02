package com.kosta.damain;

import com.kosta.entity.Post;
import com.kosta.entity.User;

import lombok.Data;

@Data
public class PostRequest {

	private Long id;
	private String title, content, password;
	private Long authorId;
	
	public Post toEntity(User author) {
		return Post.builder()
				.title(title)
				.content(content)
				.password(password)
				.author(author)
				.build();
	}
	
}
