package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.dto.CoffeeDTO;


public interface CoffeeService {

	// 커피 메뉴 전체 화면
	List<CoffeeDTO> getAllCoffeePosts() throws Exception;

	// 메뉴 추가
	void addCoffeePost(CoffeeDTO coffeeDTO, List<MultipartFile> files) throws Exception;

	// 메뉴 삭제
	void deleteCoffeePost(int id) throws Exception;

	// 메뉴 상세보기
	CoffeeDTO getCoffeePost(int id) throws Exception;

	// 메뉴 수정
	void modifyCoffeePost(CoffeeDTO coffeeDTO, List<MultipartFile> files) throws Exception;
	

	

}
