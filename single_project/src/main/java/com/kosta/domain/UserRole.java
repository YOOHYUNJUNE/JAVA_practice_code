package com.kosta.domain;

public enum UserRole {
	// LMS
//	USER("ROLE_USER"),
//	TEACHER("ROLE_TEACHER");
	
	// ECOMMERCE, SECONDHAND
//	USER("ROLE_USER"),
//	ADMIN("ROLE_ADMIN");
	
	// STUDYGROUP
	MEMBER("ROLE_MEMBER"),
	LEADER("ROLE_LEADER"),
	ADMIN("ROLE_ADMIN");
	
	String role;
	
	UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public static UserRole toUserRolefromString(String role) {
		for (UserRole userRole : UserRole.values()) {
			if(userRole.getRole().equalsIgnoreCase(role)) {
				return userRole;
			}
		}
		return MEMBER;
	}
}
