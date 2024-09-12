package com.kosta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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

import com.kosta.domain.request.ProductRequest;
import com.kosta.domain.response.ProductResponse;
import com.kosta.service.ImageFileService;
import com.kosta.service.ProductService;

import lombok.RequiredArgsConstructor;

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

		
		ProductResponse savedProduct = productService.insertProduct(productRequest, file);
		
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
		ProductResponse updatedProduct = productService.updateProduct(productRequest, file);
		
		return ResponseEntity.ok(updatedProduct);
	}
	
	
	// 상품 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductResponse> removeProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
		ProductResponse deletedProduct = productService.deleteProduct(id, productRequest);
		
		return ResponseEntity.ok(deletedProduct);
	}
	
	
	// 검색 기능
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("keyword") String keyword) {
		List<ProductResponse> result = productService.search(keyword);
		
		return ResponseEntity.ok(result);
	}
	
	
	
	
}
