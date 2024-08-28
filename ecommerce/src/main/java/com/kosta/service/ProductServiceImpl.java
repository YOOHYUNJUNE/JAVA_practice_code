package com.kosta.service;

import java.util.List;
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
	
	
	// 상품 전체 리스트 페이지(모든 권한 가능)
	@Override
	public List<Product> findAllProduct() {
		return pr.findAll();
	}

	
	// 상품 상세 보기(모든 권한 가능)
	@Override
	public Product findById(Long id) throws Exception {
		Optional<Product> optProduct = pr.findById(id);
		Product product = optProduct.orElseThrow(() -> new Exception("상품 정보가 없습니다."));
		return product;
	}
	
	
	// 상품 등록(admin)
	@Override
	public Product save(Product product, User user) {
		product.setAdmin(user);
		return pr.save(product);
	}

	// 상품 수정(admin)
	@Override
	public Product update(Product product, User user) throws Exception {
		Product originProduct = findById(product.getId());
//		User admin = originProduct.getAdmin();
		
		originProduct.setName(product.getName());
		originProduct.setEname(product.getEname());
		originProduct.setDetail(product.getDetail());
		originProduct.setImg(product.getImg());
		originProduct.setTag(product.getTag());
		originProduct.setPrice(product.getPrice());
		
		Product updatedProduct = pr.save(originProduct);
		
		// 수정한 관리자의 이름으로 변경
		originProduct.setAdmin(user);
		
		return updatedProduct;
	}

	// 상품 삭제(admin)
	@Override
	public void deleteById(Long id, User user) throws Exception {
		Product deleteProduct = findById(id);
//		User admin = product.getAdmin();
		pr.deleteById(deleteProduct.getId());
	}


	// 제품 검색, 정렬 기능
	@Override
	public List<Product> searchAndOrder(String keyword, String order) {
		if (order.equals("desc")) {
			return pr.findByNameContainsOrderByNameDesc(keyword);
		}
		return pr.findByNameContainsOrderByNameAsc(keyword);			

		
	}



}
