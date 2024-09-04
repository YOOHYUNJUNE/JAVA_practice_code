package com.kosta.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.kosta.damain.ErrorResponse;
import com.kosta.damain.FileDTO;
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
	
	// application.yml의 location 정보 가져오기(파일 다운로드)
	@Value("${spring.upload.location}")
	private String uploadPath;
	
	
	// 게시글 추가: 이미지도 추가하기
	// postMan에서 raw가 아니라 formData (이미지도 추가하기 위해)
	@PostMapping("")
	public ResponseEntity<PostResponse> writePost(PostRequest post, @RequestParam(name = "image", required = false) MultipartFile file) {
		PostResponse savedPost = postService.insertPost(post, file);
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
	public ResponseEntity<PostResponse> modifyPost(PostRequest post, @RequestParam(name = "image", required = false) MultipartFile file) {
		PostResponse updatedPost = postService.updatePost(post, file);
		return ResponseEntity.ok(updatedPost);
	}
	
	

	// 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<PostResponse> removePost(@PathVariable("id") Long id, @RequestBody PostRequest postRequest) {
		PostResponse deletedPost = postService.deletePost(id, postRequest);
		return ResponseEntity.ok(deletedPost);
	}
	
	
	
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
	
	
	
	// 파일 다운로드
	@GetMapping("/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(@PathVariable("imageId") Long id) throws MalformedURLException {
		FileDTO fileDTO = postService.getImageByImageId(id);
		
		UrlResource resource = new UrlResource("file:" + uploadPath + "\\" + fileDTO.getSaved());
		String fileName = UriUtils.encode(fileDTO.getOrigin(), StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + fileName + "\"";
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);
		
	}
	
	
	
	
	
	
	
}
