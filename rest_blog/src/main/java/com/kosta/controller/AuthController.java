package com.kosta.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.domain.LoginRequest;
import com.kosta.domain.LoginResponse;
import com.kosta.domain.SignUpRequest;
import com.kosta.domain.UserDeleteRequest;
import com.kosta.domain.UserResponse;
import com.kosta.domain.UserUpdateRequest;
import com.kosta.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		log.info("[signUp] 회원가입 진행. 요청정보 : {}", signUpRequest);
		UserResponse userResponse = authService.signUp(signUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);				
	}
	
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		log.info("[login] 로그인 시도, user : {}", loginRequest);
		LoginResponse loginResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
		return ResponseEntity.ok(loginResponse);
	}
	
	
	// 가입시 이메일 중복 체크
	@GetMapping("/duplicate")
	public ResponseEntity<Boolean> emailCheck(@RequestParam("email") String email) {
//		System.out.println(email.get("email"));
		boolean isNotDuplicate = authService.duplicateCheckEmail(email);
		return ResponseEntity.ok(isNotDuplicate);
	}
	
	
	
	// 회원 전체 리스트
	@GetMapping("")
	public ResponseEntity<List<UserResponse>> getUserList() {
		log.info("[getUserList] 회원 전체 조회");
		List<UserResponse> userList = authService.getUserList();
//		throw new RuntimeException("fsadfsadfs");
		return ResponseEntity.ok(userList);
	}
	
	// 회원 정보 수정
	@PatchMapping("")
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest userUpdateReqeust) {
		log.info("[updateUser] 회원 정보 수정. 수정 요청 정보 : {}", userUpdateReqeust);
		UserResponse userResponse = authService.updateUser(userUpdateReqeust);
		return ResponseEntity.ok(userResponse);
	}
	
	// 회원 삭제
	@DeleteMapping("")
	public ResponseEntity<?> userWithdrawal(@RequestBody UserDeleteRequest userDeleteRequest) {
		log.info("[updateUser] 회원 삭제. 삭제 요청 정보 : {}", userDeleteRequest);
		authService.deleteUser(userDeleteRequest);
		return ResponseEntity.ok(null);
	}
	
	

	
	
	
	
	
}