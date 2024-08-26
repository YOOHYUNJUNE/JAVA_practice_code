package com.kosta.common;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로거 자동 생성
@ControllerAdvice // 스프링 MVC에서 글로벌 예외 처리
public class ExceptionHandlers {

	// 특정 예외 타입에 대한 핸들러 메소드 정의
	@ExceptionHandler(Exception.class)
	public String defaultExceptionHandler(Exception e, Model model) {
		log.error(e.getMessage());
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		model.addAttribute("errMsg", e.getMessage());
		model.addAttribute("stackTrace", sw);
		return "error";
	}
	
	
}
