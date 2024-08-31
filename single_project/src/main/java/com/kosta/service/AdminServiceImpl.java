package com.kosta.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kosta.domain.UserDTO;
import com.kosta.domain.UserRole;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	private final UserRepository userRepository;
	
	@Override
	public List<UserDTO> findAllUser(Long id) {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(u ->
			UserDTO.setUserDTO(u)
		).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateUser(Long adminId, UserDTO userDTO) {
		UserRole role = userRepository.findById(adminId).get().getRole();
		if(role == UserRole.ADMIN) {
			User user = userRepository.findById(userDTO.getId()).get();
			user.setLoginAttempt(userDTO.isLocked() ? 5 : 0);
			user.setRole(UserRole.toUserRolefromString("ROLE_" + userDTO.getRole()));
			userRepository.save(user);
		}
	}
	
}
