package com.kosta.service;

import java.util.List;

import com.kosta.domain.UserDTO;

public interface AdminService {

	List<UserDTO> findAllUser(Long id);

	void updateUser(Long adminId, UserDTO userDTO);

}
