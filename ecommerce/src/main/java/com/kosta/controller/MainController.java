package com.kosta.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kosta.domain.UserDTO;
import com.kosta.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	// 비 로그인 유저(로그아웃 포함)
	
	private final UserService us;
	
	
	// 메인화면
	@GetMapping("/index")
	public String mainPage() {
		return "index";
	}
	
	// /login 화면
	@GetMapping("/login")
	public String loginPage() {
		return us.islogin() ? "redirect:/" : "user/login";
	}

	
	
	// /join 화면
	@GetMapping("/join")
	public String joinPage() {
		return us.islogin() ? "redirect:/" : "user/join";
	}

	
	// /join 동작
	@PostMapping("/join")
	public String join(UserDTO userDTO) { // 유정 정보 중 일부만 가져오기 위해 DTO 생성
		us.join(userDTO);
		return "redirect:/login";
	}
	
	
//	// /logout 동작 // Config에서 logoutUrl로 생략가능
//	@GetMapping("/logout")
//	public String logout(HttpServletRequest req, HttpServletResponse res) {
//		new SecurityContextLogoutHandler()
//		.logout(req, res, SecurityContextHolder.getContext().getAuthentication());
//		return "redirect:/";
//	}
//	
	
	
	
}
