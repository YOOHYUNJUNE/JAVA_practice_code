package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.ProductRequest;
import com.kosta.domain.response.ProductResponse;

public interface ProductService {

	ProductResponse insertProduct(ProductRequest productRequest, MultipartFile file);

	List<ProductResponse> getAllProduct();

	ProductResponse getProductById(Long id);

	ProductResponse updateProduct(ProductRequest productRequest, MultipartFile file);

	ProductResponse deleteProduct(Long id, ProductRequest productRequest);

	List<ProductResponse> search(String keyword);

}
