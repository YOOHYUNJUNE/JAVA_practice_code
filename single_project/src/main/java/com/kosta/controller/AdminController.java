package com.kosta.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.domain.UserDTO;
import com.kosta.entity.User;
import com.kosta.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;
	
	@GetMapping("")
	public String adminPage(@AuthenticationPrincipal User user, Model model) {
		List<UserDTO> userList = adminService.findAllUser(user.getId());
		model.addAttribute("list", userList);
		return "admin/admin";
	}
	
	@PatchMapping("/save")
	public String updateUser(@AuthenticationPrincipal User admin, UserDTO userDTO) {
		adminService.updateUser(admin.getId(), userDTO);
		System.out.println(userDTO);
		return "redirect:/admin";
	}
	
}
