package com.coffee.dao;

import java.util.List;

import com.coffee.model.Coffee;

public interface CoffeeDAO {

	// 메뉴 추가
	void addCoffee(Coffee coffee) throws Exception;
	
	// 메뉴 전체 보기
	List<Coffee> getAll() throws Exception;
	
	// 메뉴 상세 설명
	Coffee getCoffee(int id) throws Exception;
	
	// 메뉴 삭제
	void deleteCoffee(int id) throws Exception;
	
	// 메뉴 수정
	void modifyCoffee(Coffee coffee) throws Exception;
	
	
}
