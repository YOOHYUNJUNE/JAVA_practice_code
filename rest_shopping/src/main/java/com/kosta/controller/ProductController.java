package com.kosta.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.domain.ProductRequestDTO;
import com.kosta.domain.ProductResponseDTO;
import com.kosta.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	private final ProductService ps;
	
	@GetMapping("/product")
	@ResponseBody
	public List<ProductResponseDTO> getProducts() {
		List<ProductResponseDTO> productList = ps.getAllProducts();
		return productList; 
	}
	
	@PostMapping("/product")
	@ResponseBody
	public ProductResponseDTO postProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		ProductResponseDTO product = ps.addProduct(productRequestDTO);
		return product; 
	}
}
