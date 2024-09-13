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

import com.kosta.domain.request.JoinRequest;
import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.LoginResponse;
import com.kosta.domain.response.UserResponse;
import com.kosta.service.UserService;
import com.kosta.util.TokenUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommonController {

	private final UserService userService;
	private final TokenUtils tokenUtils;
	
	// 토큰 재발급 요청
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest req, HttpServletResponse res) {
		// 토큰 요청
		Map<String, String> tokenMap = userService.refreshToken(req);
		// 토큰 재발급 불가시, 401 반환
		if (tokenMap == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);	
		}
		// 헤더 쿠키로 리프레시 토큰 재발급
		tokenUtils.setRefreshTokenCookie(res, tokenMap.get("refreshToken"));
		// tokenMap 없는 경우, 응답 body로 액세스 토큰 재발급
		return ResponseEntity.ok(
				LoginResponse.builder()
				.accessToken(tokenMap.get("accessToken"))
				.build());				
	}
	
	// 회원가입
	@PostMapping("/userjoin")
	public ResponseEntity<UserResponse> join(@RequestBody JoinRequest joinRequest) {
		log.info("[join] 회원가입 진행 : {}", joinRequest);
		UserResponse userResponse = userService.join(joinRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
	}
	
	// 회원가입시 이메일 중복 체크
	@GetMapping("/duplicate")
	public ResponseEntity<Boolean> emailCheck(@RequestParam("email") String email) {
		log.info("[duplicate] 이메일 중복 체크 : {}", email);
		boolean isNotDuplicate = userService.duplicateCheckEmail(email);
		
		return ResponseEntity.ok(isNotDuplicate);
	}
	
	// 로그인 -> LoginCustomAuthenticationFilter
	
	
	// 회원 전체 리스트
	@GetMapping("/userlist")
	public ResponseEntity<List<UserResponse>> getUserList() {
		log.info("[getUserList] 회원 전체 조회");
		List<UserResponse> userList = userService.getUserList();
		
		return ResponseEntity.ok(userList);
	}
	
	
	// 회원 정보 수정
	@PatchMapping("/usermodify")
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequset) {
		log.info("[updateUser] 회원 정보 수정 : {}", userUpdateRequset);
		UserResponse userResponse = userService.updateUser(userUpdateRequset);
		
		return ResponseEntity.ok(userResponse);
	}
	
	
	// 회원 탈퇴
	@DeleteMapping("/userdelete")
	public ResponseEntity<?> userWithdrawal(@RequestBody UserDeleteRequest userDeleteRequest) {
		log.info("회원 탈퇴 : {}", userDeleteRequest);
		userService.deleteUser(userDeleteRequest);
		
		return ResponseEntity.ok(null);
	}
	
	
	
	
	
	
	
	
	
	
}
