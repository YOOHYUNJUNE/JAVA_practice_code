package com.coffee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.coffee.model.Coffee;

public interface CoffeeService {
	
	// 메뉴 추가
	void addCoffee(HttpServletRequest req) throws Exception;
	
	// 메뉴 전체 보기
	List<Coffee> getAll() throws Exception;
	
	// 메뉴 상세 설명
	Coffee getCoffee(HttpServletRequest req) throws Exception;
	
	// 메뉴 삭제
	void deleteCoffee(HttpServletRequest req) throws Exception;
	
	// 메뉴 수정
	void modifyCoffee(HttpServletRequest req) throws Exception;

}
