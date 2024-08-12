package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 해당 클래스가 RestController 기능을 수행 : Bean 주입
@RestController
public class HelloController {
	@RequestMapping("/") // 메소드가 실행할 수 있는 경로 설정
	public String hello() {
		return "안녕~ 스프링아 고맙다~";
	}
	
	@RequestMapping("/hello") // 메소드가 실행할 수 있는 경로 설정
	public String hello2() {
		return "Hello Spring~~~";
	}
	
	
}
