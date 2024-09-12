package com.kosta.service;

import java.util.List;
import java.util.Map;

import com.kosta.domain.request.JoinRequest;
import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.UserResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	UserResponse join(JoinRequest joinRequest);

	boolean duplicateCheckEmail(String email);

	Map<String, String> refreshToken(HttpServletRequest req);

	List<UserResponse> getUserList();

	UserResponse updateUser(UserUpdateRequest userUpdateRequset);

	void deleteUser(UserDeleteRequest userDeleteRequest);

}
