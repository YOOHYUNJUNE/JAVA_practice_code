package com.kosta.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.entity.Product;
import com.kosta.entity.User;
import com.kosta.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository pr;
	
	// 상품 보기(모든 권한 가능)
	@Override
	public Product findById(Long id) {
		Optional<Product> optProduct = pr.findById(id);
		Product product = optProduct.orElseThrow(() -> new Exception("상품 정보가 없습니다."));
		return product;
	}
	
	
	// 상품 등록(admin)
	@Override
	public Product save(Product product, User user) {
		product.setCreator(user);
		return pr.save(product);
	}

	// 상품 수정(admin)
	@Override
	public Product update(Product product, User user) {
		Product originProduct = findById(product.getId());
		User creator = originProduct.getCreator();
	}

	@Override
	public void deleteById(Long id, User user) {
		Product product = findById(id);
		User creator = product.getCreator();
		pr.deleteById(product.getId());
	}

}
