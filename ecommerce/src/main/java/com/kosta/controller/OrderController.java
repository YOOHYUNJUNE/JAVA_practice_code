package com.kosta.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.entity.Ordering;
import com.kosta.entity.Product;
import com.kosta.entity.User;
import com.kosta.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/*")
public class OrderController {
	
	private final OrderService os;

	// 주문기능
	// product detail > 구매하기 버튼 > USER만 가능(나머지 로그인창으로 이동) > 누르면 토글에 개수 입력
	
	// 주문 추가
	@PostMapping("/add")
	public String addOrder(Ordering ordering, @AuthenticationPrincipal User user) {
		os.save(ordering, user);
		return "redirect:/list";
	}
	
	
	// 주문 취소
	@DeleteMapping("/delete/{id}")
	public String deleteOrder(@PathVariable("id") Long id, @AuthenticationPrincipal User user) throws Exception {
		os.deleteById(id, user);
		return "redirect:/user/detail/" + user.getId();
	}
	
	
	
	
	
}
