package com.board.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor{
	
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("================================= 시작 ================================================");
		log.info("Request URI \t : {}", request.getRequestURI());
		

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		log.info("=================================== 끝 ==================================== \n");
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
	
}
