package com.kosta.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.entity.Article;
import com.kosta.service.BlogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BlogController {

	private final BlogService blogService;
	
	
	// 게시글 추가 페이지
	@GetMapping("/add")	
	public String addPage() {
		return "form";
	}
	
	
	// 게시글 추가
	@PostMapping("/add")
	public String addArticle(Article article) {
		blogService.save(article);
		return "redirect:/list"; // redirect: 리프레시할 때 중복된 요청 방지
	}
	
	
	// 게시글 전체 목록
	@GetMapping("/list")
	public String listPage(
			@RequestParam(required = false, name = "keyword") String keyword, // 검색
			@RequestParam(required = false, name = "order") String order, // 정렬
			Model model) {
		List<Article> articleList;
		if (keyword != null) {
			articleList = blogService.searchInTitleAndContent(keyword);
		} else if (order != null) {
			articleList = blogService.orderingArticleList(order);
		} else {
			articleList = blogService.findAll();
		}	
		model.addAttribute("list", articleList);
		return "list";
	}
	
	
	// 특정 게시물 찾기
	@GetMapping("/detail/{id}")
	public String detailPage(@PathVariable("id") Long id, Model model) {
		try {
			Article article = blogService.findById(id);
			model.addAttribute("article", article);
			return "detail";
		} catch (Exception e) {			
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	
	
	// 게시물 삭제
	@DeleteMapping("/delete/{id}")
	public String deleteArticle(@PathVariable("id") Long id, Model model) {
		try {
			blogService.deleteById(id);
			return "redirect:/list";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	
	
	// 게시물 수정 화면
	@GetMapping("/modify/{id}")
	public String modifyPage(@PathVariable("id") Long id, Model model) {
		try {
			Article article = blogService.findById(id);
			model.addAttribute("article", article);
			return "form";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	
	
	// 게시물 수정
	@PatchMapping("/modify")
	public String modifyArticle(Article article, Model model) {
		try {
			blogService.update(article);
			return "redirect:/detail/" + article.getId();
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	
	
	
	
	
	
	
}
