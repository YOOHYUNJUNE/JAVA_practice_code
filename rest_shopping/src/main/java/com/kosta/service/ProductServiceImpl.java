package com.kosta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kosta.domain.ProductRequestDTO;
import com.kosta.domain.ProductResponseDTO;
import com.kosta.entity.Product;
import com.kosta.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository pr;

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		List<Product> productList = pr.findAll();
		List<ProductResponseDTO> list = productList.stream().map(p -> ProductResponseDTO.setDTO(p)).toList();
		return list;
	}

	@Override
	public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
		Product product = productRequestDTO.setEntity();
		Product savedProduct = pr.save(product);
		return ProductResponseDTO.setDTO(savedProduct);
	}
	
}
