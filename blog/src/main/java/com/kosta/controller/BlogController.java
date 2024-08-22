package com.kosta.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kosta.entity.BlogEntity;
import com.kosta.service.BlogService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class BlogController {

	private final BlogService bs;
	
	
	// 게시글 추가 페이지
	@GetMapping("/add")
	public String addPage() {
		return "form";
	}
	
	
	// 게시글 추가
	@PostMapping("/add")
	public String addArticle(BlogEntity blogEntity) throws Exception {
		bs.save(blogEntity);
		return "redirect:/list";
	}
	
	
	// 게시글 전체 목록
	@GetMapping("/list")
	public String listPage(Model model) {
		List<BlogEntity> blogList = bs.findAll();
		model.addAttribute("list", blogList);
		return "list";
	}
	
	
	// 게시물 상세보기
	@GetMapping("/detail/{id}")
	public String detailPage(@PathVariable("id") int id, Model model) throws Exception {
		BlogEntity dto = bs.findById(id);
		model.addAttribute("blogEntity", dto);
		return "detail";
	}
	
	
	// 게시물 삭제
	@DeleteMapping("/delete/{id}")
	public String deletePage(@PathVariable("id") int id, Model model) throws Exception {
		bs.deleteById(id);
		return "redirect:/list";
	}
	
	
	
	// 게시물 수정페이지
	@GetMapping("/modify/{id}")
	public String modifyPage(@PathVariable("id") int id, Model model) throws Exception {
		BlogEntity page = bs.findById(id);
		model.addAttribute("blogEntity", page);
		return "form";
	}
	
	
	// 게시물 수정
	@PatchMapping("/modify")
	public String modifyPost(BlogEntity blogEntity) throws Exception {
		bs.update(blogEntity);
		return "redirect:/detail/" + blogEntity.getId();
	}
	
	
	
	
	
	
	
}
