package com.kosta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.dto.CoffeeDTO;
import com.kosta.service.CoffeeService;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {
	
	@Autowired
	CoffeeService cs;

	// 커피 메뉴 전체 화면
	@RequestMapping(value = { "", "/", "list"})
	public ModelAndView showCoffeeList() throws Exception {
		ModelAndView mav = new ModelAndView("coffee/list");
		// 메뉴 전체 가져오기
		List<CoffeeDTO> CoffeeList = cs.getAllCoffeePosts();
		mav.addObject("list", CoffeeList);
		return mav;
	}
	
	
	// 메뉴 추가 화면
	@GetMapping("/write")
	public String showCoffeeWriteForm() throws Exception {
		return "coffee/write";
	}
	
	
	// 메뉴 추가
	@PostMapping("/write")
	public String writeCoffeePost(CoffeeDTO coffeeDTO, @RequestParam("files") List<MultipartFile> files) throws Exception {
		// 메뉴 등록
		cs.addCoffeePost(coffeeDTO, files);
		return "redirect:/coffee";
	}
	
	
	// 메뉴 삭제
	@DeleteMapping("/delete")
	public String deleteCoffeePost(@RequestParam("id") int id) throws Exception {
		// 메뉴 삭제
		cs.deleteCoffeePost(id);
		return "redirect:/coffee";
	}
	
	
	
	// 메뉴 수정 화면
	@GetMapping("/modify")
	public ModelAndView showCoffeeModifyForm(@RequestParam("id") int id) throws Exception {
		ModelAndView mav = new ModelAndView("coffee/write");
		// 수정하기 위해 메뉴 정보 가져오기
		CoffeeDTO coffeeDTO = cs.getCoffeePost(id);
		mav.addObject("coffee", coffeeDTO);
		return mav;
	}
	
	
	
	// 메뉴 수정
	@PatchMapping("/modify")
	public String modifyCoffeePost(CoffeeDTO coffeeDTO, @RequestParam("files") List<MultipartFile> files) throws Exception {
		// 메뉴 수정
		cs.modifyCoffeePost(coffeeDTO, files);
		return "redirect:/coffee";
	}
	
	
	
	// 메뉴 상세보기
	@GetMapping("/detail/{id}")
	public ModelAndView showCoffeeDetail(@PathVariable int id) throws Exception {
		ModelAndView mav = new ModelAndView("coffee/detail");
		// 상세정보
		CoffeeDTO coffeeDTO = cs.getCoffeePost(id);
		mav.addObject("coffee", coffeeDTO);
		return mav;
	}
	
	
	// 파일 이미지 다운로드
	
	
	
	
	
	
}
