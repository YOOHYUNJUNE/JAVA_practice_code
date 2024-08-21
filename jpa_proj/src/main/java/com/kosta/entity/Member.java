package com.kosta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "member_tbl")
@NoArgsConstructor // 기본생성자
@Data
public class Member {

	// BD의 tbl 컬럼의 역할까지 부여
	
	@Id // 기본키
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false) // not null
	private String name;

	
	// 빌더(롬복)
	@Builder
	public Member(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	
	
	
	
}
