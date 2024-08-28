package com.kosta.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kosta.domain.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@Data
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	@Column(nullable = false, unique = true) // 닉네임
	private String nickname;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true) // 로그인용 이메일
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER; // 가입시 기본 USER
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;


	// 현재 유저 목록의 ROLE을 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> roleList = new ArrayList<>();
		roleList.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
		return roleList;
	}

	// 로그인시 사용할 username을 email로 사용
	@Override
	public String getUsername() {
		return this.email;
	}


	
	
}
