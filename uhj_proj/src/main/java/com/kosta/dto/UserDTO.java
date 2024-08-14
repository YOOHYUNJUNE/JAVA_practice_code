package com.kosta.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {

	private int id;
	private String name, email;
	private LocalDateTime createdAt;
	// 나머지는 안보여줄 것임
}
