package com.kosta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.dto.UserDTO;
import com.kosta.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper um;
	
	@Override
	public List<UserDTO> getAllUserList() throws Exception {
		return um.selectAllUser();
	}

	
	@Override
	public void addUser(UserDTO userDTO) throws Exception {
		um.insertUser(userDTO);
	}

	
	@Override
	public void removeUser(int id) throws Exception {
		um.deleteUser(id);
	}


	// 유저 정보 가져오기
	@Override
	public UserDTO getUserById(int id) throws Exception {
		return um.selectUserById(id);
	}

	
}
