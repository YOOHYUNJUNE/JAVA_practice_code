package com.kosta.service;

import com.kosta.domain.UserDTO;

public interface UserService {

	void join(UserDTO userDTO, String ip);

}
