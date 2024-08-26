package com.kosta.domain;

public enum UserRole {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	
	String role;
	
	UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
