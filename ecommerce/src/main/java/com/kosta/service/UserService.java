package com.kosta.service;

import java.util.List;

import com.kosta.domain.UserDTO;
import com.kosta.entity.User;

public interface UserService {

	// 로그인 여부
	boolean islogin();

	// 회원가입
	void join(UserDTO userDTO);

	// 유저 전체(admin 권한)
	List<User> findAllUser();


}
