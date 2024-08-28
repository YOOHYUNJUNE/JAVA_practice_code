package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.domain.UserDTO;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository ur;
	private final BCryptPasswordEncoder bc;

	@Override
	public boolean islogin() {
		return false;
	}


	// UserDTO를 만들어서 유저 정보 일부 컬럼만 가져오기
	@Override
	public void join(UserDTO userDTO) {
		String password = userDTO.getPassword();
		String encodedPassword = bc.encode(password);
		userDTO.setPassword(encodedPassword);;
		ur.save(userDTO.setUser());
	}


	// 모든 유저 리스트(ADMIN)
	@Override
	public List<User> findAllUser() {
		return ur.findAll();
	}

	// 유저 상세 보기(로그인한 유저만)
	@Override
	public User findById(Long id) throws Exception {
		Optional<User> optUser = ur.findById(id);
		User user = optUser.orElseThrow(() -> new Exception("유저 정보가 없습니다"));
		return user;
	}

	// 유저 수정(ADMIN)
	@Override
	public User update(User user) throws Exception {
		User originUser = findById(user.getId());
		
		originUser.setName(user.getName());
		originUser.setNickname(user.getNickname());
		originUser.setRole(user.getRole());
		
		User updatedUser = ur.save(originUser);
		return updatedUser;
	}


	// 유저 탈퇴
	@Override
	public void deleteById(Long id, User user) throws Exception {
		User deleteUser = findById(id);
		ur.deleteById(deleteUser.getId());
	}


	


}
