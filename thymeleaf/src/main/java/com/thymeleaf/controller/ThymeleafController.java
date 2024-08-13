package com.thymeleaf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.thymeleaf.dto.UserDTO;



@Controller
public class ThymeleafController {
	// 타임리프 : 자바 기반의 서버 템플릿 엔진
	// HTML 파일에서 서버 데이터를 표현하고 조작할 수 있게 도와줌
	
	
	
	@RequestMapping("/")
	public ModelAndView showMainpage() {
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}
	
	
	
	
	
	
	@RequestMapping("/variable")
	public ModelAndView varExpression() {
		ModelAndView mav = new ModelAndView("var");
		mav.addObject("variable1", "변수 데이터");
		mav.addObject("variable2", "<strong>변수 데이터2<strong>");
		
		UserDTO userDTO = new UserDTO(1, "유현준", "송파구");
		mav.addObject("user", userDTO);
		
		return mav;
	}
	
	
	// search에서 이름 전달
	@RequestMapping("/search")
	public ModelAndView searchResult(@RequestParam("keyword") String keyword) {
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("keyword", keyword);
		
		List<UserDTO> userList = new ArrayList<>();
		userList.add(new UserDTO(2, "홍길동", "서울"));
		userList.add(new UserDTO(3, "임꺽정", "대전"));
		userList.add(new UserDTO(4, "손흥민", "서울"));
		userList.add(new UserDTO(5, "차범근", "부산"));
		userList.add(new UserDTO(5, "김길동", null));
		
		mav.addObject("list", userList);
		
		return mav;
	}	
	
	
	

	
	
	
	
	
	
	
}
