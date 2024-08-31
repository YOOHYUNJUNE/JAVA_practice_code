//package com.kosta.entity;
//
//import java.time.LocalDateTime;
//
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@RequiredArgsConstructor
//@Data
//public class Course {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(updatable = false)
//	private Long id;
//
//	@Column(nullable = false)
//	private String name;
//	
//	@Column(nullable = false)
//	private String description;
//	
//	@Column(name = "hour_period")
//	private int hourPeriod;
//	
//	@JoinColumn(name = "teacher_id")
//	@ManyToOne
//	private User teacher;
//	
//	@CreatedDate
//	@Column(name = "created_at")
//	private LocalDateTime createdAt;
//
//	@LastModifiedDate
//	@Column(name = "updated_at")
//	private LocalDateTime updatedAt;
//}
