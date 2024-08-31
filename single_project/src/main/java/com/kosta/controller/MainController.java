package com.kosta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.domain.UserDTO;
import com.kosta.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final UserService userService;
	
	@GetMapping("/join")
	public String joinPage(HttpServletRequest req) {
		
		return "user/join";
	}
	
	@PostMapping("/join")
	public String join(UserDTO userDTO, HttpServletRequest req) {
		String ip = req.getRemoteAddr();
		userService.join(userDTO, ip);
		return "redirect:/";
	}
}
