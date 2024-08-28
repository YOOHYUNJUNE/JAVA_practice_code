package com.kosta.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.entity.Ordering;
import com.kosta.entity.User;
import com.kosta.service.OrderService;
import com.kosta.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/*")
public class UserController {

	private final UserService us;
	private final OrderService os;
	
	// ADMIN : 모든 유저 수정 및 삭제 가능
	// USER : 자기 자신만 수정 및 삭제 가능
	
	
	// 유저 상세 정보
	@GetMapping("/detail/{id}")
	public String userDetailPage(@PathVariable("id") Long id, Model model) throws Exception {
		User user = us.findById(id);
		model.addAttribute("user", user);
				
		// 유저의 주문 조회
		List<Ordering> orders = os.findOrderByUser(id);
		model.addAttribute("orders", orders);
		
		
		return "user/userdetail";
	}
	
	// 유저 수정 페이지
	@GetMapping("/modify/{id}")
	public String userModifyPage(@PathVariable("id") Long id, Model model) throws Exception {
		User user = us.findById(id);
		model.addAttribute("user", user);
		return "user/userform";
	}
	
	
	// 유저 정보 수정
	@PatchMapping("/modify")
	public String modifyUser(User user, Model model) throws Exception {
		us.update(user);
		return "redirect:/user/detail/" + user.getId();
		
		
	}
	
	
	// 유저 탈퇴
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) throws Exception {
		us.deleteById(id, user);
		
		// ADMIN이면 userlist로
		if (user.getRole().equals("ADMIN")) {
			return "redirect:/userlist";			
			// USER면 index로
		} else {
			
			return "redirect:/index";
		}
	
	
	
	
	}
}
