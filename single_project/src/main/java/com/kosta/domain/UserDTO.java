package com.kosta.domain;

import com.kosta.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String username, name, password;
	private boolean locked;
	private String role;
	
	public User setUser() {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setName(name);
		user.setPassword(password);
		return user;
	}
	
	public static UserDTO setUserDTO(User user) {
		return UserDTO.builder()
		.id(user.getId())
		.name(user.getName())
		.username(user.getUsername())
		.locked(user.getLoginAttempt() >= 5)
		.role(user.getRole().getRole())
		.build();
	}
}
