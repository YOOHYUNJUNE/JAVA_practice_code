package com.kosta.domain;

import com.kosta.entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {

	private Long id;
	private String email, name, password, nickname;
	private UserRole role;
	
	@Builder
	public UserDTO(Long id, String email, String name, String nickname, String password, UserRole role) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}
	
	public User setUser() {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setName(name);
		user.setNickname(nickname);
		user.setPassword(password);
		return user;
	}
	
	
}
