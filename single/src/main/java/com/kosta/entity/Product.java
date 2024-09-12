package com.kosta.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String ename = "영어 이름";
	
	@Column
	private int price = 0;
	
	@Column
	private String iceOrHot = "아이스or핫";
	
	@Column
	private Double sugar = 0.0;
	@Column
	private Double caffeine = 0.0;
	@Column
	private Double calorie = 0.0; 
	@Column
	private String allergy = "알레르기 정보";
	@Column
	private String detail;
	
	// 관리자
	@JoinColumn(name = "author_id", nullable = false)
	@ManyToOne
	private User author;
	
	// 이미지
	@JoinColumn(name = "image_id", nullable = true)
	@ManyToOne
	private ImageFile image;

	
	
	
	
}
