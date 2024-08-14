package com.kosta.service;

import java.util.List;

import com.kosta.dto.UserDTO;

public interface UserService {

	// 삭제 안된 회원 리스트 가져오기
	List<UserDTO> getAllUserList() throws Exception;
	
	// 회원 추가
	void addUser(UserDTO userDTO) throws Exception;
	
	// 회원 삭제
	void removeUser(int id) throws Exception;
	
	// 회원 가져오기
	UserDTO getUserById(int id) throws Exception;
	
}
