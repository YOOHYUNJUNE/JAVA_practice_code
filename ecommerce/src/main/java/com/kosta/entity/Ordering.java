package com.kosta.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@Data
public class Ordering {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	@Column(nullable = false)
	private int quantity; // 수량
	
	// 관계설정 : 구매자
	@JoinColumn(name = "customer_id")
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User customer;
	
	// 관계설정 : 상품
	@JoinColumn(name = "product_id")
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;
	
	// 총 가격은 컬럼 불필요 : 자바스크립트로 구현
	
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	
	
	
	
	
	
}
