package com.kosta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.damain.ErrorResponse;
import com.kosta.damain.PostRequest;
import com.kosta.damain.PostResponse;
import com.kosta.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController // @ResponseBody 생략 ( @RequestBody 외 ) 
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

	private final PostService postService;
	
	
	// 추가
	@PostMapping("")
	public ResponseEntity<PostResponse> writePost(@RequestBody PostRequest post) {
		PostResponse savedPost = postService.insertPost(post);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
	}
	
	
	// 전체 게시물 조회 
	@GetMapping("")
	public ResponseEntity<List<PostResponse>> getAllPost(@RequestParam(name = "id", required = false) Long id) {
		List<PostResponse> result = new ArrayList<>();
		
		if (id == null) {
			result = postService.getAllPost();
		} else {
			PostResponse postResponse = postService.getPostById(id);
			result.add(postResponse);
		}
		return ResponseEntity.ok(result);		
	}
	
	
	// 특정 게시물 조회
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long id) {
		PostResponse postResponse = postService.getPostById(id);
		return ResponseEntity.ok(postResponse);
	}
	
	
	
	// 수정
	@PatchMapping("")
	public ResponseEntity<PostResponse> modifyPost(@RequestBody PostRequest post) {
		PostResponse updatedPost = postService.updatePost(post);
		return ResponseEntity.ok(updatedPost);
	}
	
	
	
	
	
	// 삭제
	
	
	
	// 예외처리
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handlerPostException(RuntimeException e, HttpServletRequest req) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
					ErrorResponse.builder()
						.statusCode(HttpStatus.BAD_REQUEST.value())
						.message("게시물 관련 에러 발생")
						.url(req.getRequestURI())
						.details(e.getMessage())
						.build()						
				);
	}
	
	
	
	
	
	
}
