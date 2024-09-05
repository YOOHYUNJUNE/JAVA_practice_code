package com.kosta.service;

import java.util.List;

import com.kosta.domain.SignUpRequest;
import com.kosta.domain.UserDeleteRequest;
import com.kosta.domain.UserResponse;
import com.kosta.domain.UserUpdateRequest;

public interface AuthService {

	UserResponse signUp(SignUpRequest signUpRequest);

	List<UserResponse> getUserList();

	UserResponse updateUser(UserUpdateRequest userUpdateReqeust);

	void deleteUser(UserDeleteRequest userDeleteRequest);

	boolean duplicateCheckEmail(String email);

}