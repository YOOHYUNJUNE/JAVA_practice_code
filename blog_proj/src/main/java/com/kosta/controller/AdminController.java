package com.kosta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller // 관리자 페이지 구성 컨트롤러
@RequestMapping("/admin/**")
@RequiredArgsConstructor
public class AdminController {

	@GetMapping("")
	public String adminPage() {
		return "admin/index";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
