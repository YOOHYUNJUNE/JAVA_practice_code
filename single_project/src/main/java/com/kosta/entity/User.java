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
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;

	@Column(name = "nickname", nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String ip;
	
	@Column(name = "login_attempt")
	private int loginAttempt = 0;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.MEMBER;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> roleList = new ArrayList<>();
		roleList.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
		return roleList;
	}

	@Override
	public boolean isAccountNonLocked() {
		if (loginAttempt >= 5) {
			return false;
		} return true;
	}
	
	
}
