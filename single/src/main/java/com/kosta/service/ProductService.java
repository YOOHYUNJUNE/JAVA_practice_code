package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.ProductRequest;
import com.kosta.domain.response.ProductResponse;
import com.kosta.entity.User;

public interface ProductService {

	ProductResponse insertProduct(ProductRequest productRequest, MultipartFile file, User user);

	List<ProductResponse> getAllProduct();

	ProductResponse getProductById(Long id);

	ProductResponse updateProduct(ProductRequest productRequest, MultipartFile file, User user);

	ProductResponse deleteProduct(Long id, User user);

	List<ProductResponse> search(String keyword);

}
