package com.kosta.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	@Column(nullable = false)
	private String name; // 상품명
	@Column(nullable = false)
	private String ename; // 영어명
	
	@Column
	private String detail = " "; // 상품정보
	
	// 이미지
	@Column
	public String imageFileName;
	
	@Column
	private String tag = " ";
	
	@Column(nullable = false)
	private int price = 1000;
	
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	
	
	
	
	
	// 제품 관리자
	@ManyToOne
	@JoinColumn(name = "admin_id", referencedColumnName = "id")
	private User admin;
	
	
	
	
	// 롬복 오류로 일단 전부 생성
	@Builder
	public Product(Long id, String name, String ename, String detail, String tag, int price,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.ename = ename;
		this.detail = detail;
		this.tag = tag;
		this.price = price;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}







	
	
	
	
}
