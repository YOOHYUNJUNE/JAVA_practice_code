package com.board.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.board.interceptor.LoggerInterceptor;

@Configuration // logger를 인식하게 하는 애너테이션
public class WebMvcConfiguration implements WebMvcConfigurer {

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/board/*"); // 특정 조건에만 출력되게 함
//		registry.addInterceptor(new LoggerInterceptor());
//		registry.addInterceptor(new LoggerInterceptor()); // 여러개 추가도 가능
	}
	
	
	
	
	
}
