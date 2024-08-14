package com.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coffee.dto.CoffeeDTO;
import com.coffee.service.CoffeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그
@Controller // 컨트롤러를 지정하는 애너테이션
@RequestMapping("/coffee") // 모든 경로에 coffee를 거치게 함
public class CoffeeController {
	
	@Autowired // 서비스 Bean 자동 주입
	private CoffeeService cs;
	
	@RequestMapping("/list") // 요청에 맞는 주소 지정
	public ModelAndView coffeeList() throws Exception {
		// src/main/resources/templates/coffee/list.html로 화면 지정
		ModelAndView mv = new ModelAndView("coffee/list");
		// 비즈니스 로직 수행
		List<CoffeeDTO> coffeeList = cs.selectCoffeeList();
		mv.addObject("list", coffeeList);
		return mv;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
