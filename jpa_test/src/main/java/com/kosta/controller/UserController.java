package com.kosta.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kosta.entity.User;
import com.kosta.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService us;
	
	
	// 로그인 화면
	@GetMapping("/login")
	public String loginPage() {
		return us.islogin() ? "redirect:/blog/list" : "/login";
	}
	
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		new SecurityContextLogoutHandler().logout(req, res, SecurityContextHolder.getContext().getAuthentication());
		return us.islogin() ? "redirect:/blog/list" : "/join";
	}
	
	
	
	
	// 회원가입 화면
	@GetMapping("/join")
	public String joinPage() {
		return "/join";
	}
	
	
	
	// 회원가입
	@PostMapping("/join")
	public String join(User user) {
		us.save(user);
		return "redirect:/login";
	}
	
	
	
	
	
	
	
}
