package com.kosta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "member_tbl")
@NoArgsConstructor // 기본생성자
@Data
public class Member {

	// BD의 tbl 컬럼의 역할까지 부여
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false) // null값 허용하지 않음
	private String name;
	
	
	
}
