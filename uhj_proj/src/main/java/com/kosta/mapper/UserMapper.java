package com.kosta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kosta.dto.UserDTO;

@Mapper
public interface UserMapper {

	// 삭제 안된 회원 리스트
	List<UserDTO> selectAllUser() throws Exception;
	
	// 회원 추가
	void insertUser(UserDTO userDTO) throws Exception;
	
	// 회원 삭제
	void deleteUser(int id) throws Exception;
	
	
}
