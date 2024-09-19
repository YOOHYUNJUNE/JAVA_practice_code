package com.kosta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.ProductRequest;
import com.kosta.domain.response.ProductResponse;
import com.kosta.entity.User;
import com.kosta.service.ImageFileService;
import com.kosta.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

	private final ProductService productService;
	private final ImageFileService imageFileService;
	
	// application.yml의 location 정보 가져오기
	@Value("${spring.upload.location}")
	private String uploadPath;
	
	// 상품 추가
	// postMan에서 raw가 아니라 formData (이미지도 추가하기 위해)
	@PostMapping("")
	public ResponseEntity<ProductResponse> addProduct(ProductRequest productRequest, @RequestParam(name = "image", required = false) MultipartFile file) {

		// 토큰을 통해 로그인 유저 정보를 가져옴
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ProductResponse savedProduct = productService.insertProduct(productRequest, file, user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}
	
	
	// 전체 상품 조회
	@GetMapping("")
	public ResponseEntity<List<ProductResponse>> getAllProduct(@RequestParam(name = "id", required = false) Long id) {
		List<ProductResponse> result = new ArrayList<>();
		if (id == null) {
			result = productService.getAllProduct();
		} else {
			ProductResponse ProductResponse = productService.getProductById(id);
			result.add(ProductResponse);
		}
		return ResponseEntity.ok(result);
	}
	
	
	// 특정 상품 조회
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
		ProductResponse productResponse = productService.getProductById(id);
		
		return ResponseEntity.ok(productResponse);
	}
	
	
	// 상품 수정
	@PatchMapping("")
	public ResponseEntity<ProductResponse> modifyProduct(ProductRequest productRequest, @RequestParam(name = "image", required = false) MultipartFile file) {
		// 토큰을 통해 로그인 유저 정보를 가져옴
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ProductResponse updatedProduct = productService.updateProduct(productRequest, file, user);
		
		return ResponseEntity.ok(updatedProduct);
	}
	
	
	// 상품 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductResponse> removeProduct(@PathVariable("id") Long id) {
		// 로그인 유저(토큰을 통해 유저 정보 가져옴)
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ProductResponse deletedProduct = productService.deleteProduct(id, user);

//		ProductResponse deletedProduct = productService.deleteProduct(id);
		
		return ResponseEntity.ok(deletedProduct);
	}
	
	
	// 검색 기능
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("keyword") String keyword) {
		List<ProductResponse> result = productService.search(keyword);
		
		return ResponseEntity.ok(result);
	}
	
	
//	// RestTemplate으로 타 사이트의 주소 가져오기
//	@GetMapping("/news/{id}")
//	public ResponseEntity<String> getNewsById(@PathVariable("id") int id) {
//		// RestTemplate 인스턴스 생성
//		RestTemplate rt = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json; charset=utf-8");
//		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
//		// URL 주소 만들기
//		String url = "https://jsonplaceholder.typicode.com/posts/" + id;
//		
//		try {
////			ResponseEntity<String> result = rt.getForEntity(url, String.class);		
//			ResponseEntity<String> result = rt.exchange(url, HttpMethod.GET, httpEntity, String.class);
//			return result;
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("에러 발생");
//		}
//		
//	}
	
	
	
}
