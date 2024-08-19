package com.kosta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kosta.dto.User;

@Mapper
public interface UserMapper {

	// 회원 추가
	void save(User user) throws Exception;
	
	// 회원 삭제
	void deleteById(int id) throws Exception;
	
	// 특정 회원 조회
	User findMyId(int id) throws Exception;
	
	// 회원 전체 조회
	List<User> findAll() throws Exception;
	
	// 회원 수정
	void updateUser(User user) throws Exception;
}
