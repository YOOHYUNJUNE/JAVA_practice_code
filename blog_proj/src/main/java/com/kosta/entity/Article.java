package com.kosta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity // table 이름 안 적어주면 클래스명으로 자동
@RequiredArgsConstructor
@Data
public class Article {

	// 기본키 설정
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false) // update시 이 컬럼은 변경하지 않음
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;

	
	// 빌더 패턴
	@Builder
	public Article(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	
	
}
