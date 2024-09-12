package com.kosta.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.ProductRequest;
import com.kosta.domain.response.ProductResponse;
import com.kosta.entity.ImageFile;
import com.kosta.entity.Product;
import com.kosta.entity.User;
import com.kosta.repository.ProductRepository;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final ImageFileService imageFileService;
	
	
	// 상품 추가 -> FileUtils
	@Override
	public ProductResponse insertProduct(ProductRequest productDTO, MultipartFile file) {
		// 이미지 저장
		ImageFile savedImage = imageFileService.saveImage(file);
		if (savedImage != null) productDTO.setImageFile(savedImage); 
		// 상품 추가
		User user = userRepository.findById(productDTO.getAuthorId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		Product product = productDTO.toEntity(user);
		Product savedProduct = productRepository.save(product);
		ProductResponse result = ProductResponse.toDTO(savedProduct);
		
		return result;
	}


	// 전체 상품 조회
	@Override
	public List<ProductResponse> getAllProduct() {
		List<Product> productList = productRepository.findAll();
		if (productList.size() > 0) {
			List<ProductResponse> productResponseList = productList.stream().map(ProductResponse::toDTO).toList();
			return productResponseList;
		} else {
			return new ArrayList<>();
		}
	}


	// 특정 상품 조회
	@Override
	public ProductResponse getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("제품 정보가 없습니다."));
		ProductResponse productResponse = ProductResponse.toDTO(product);
		return productResponse;
	}


	// 상품 수정
	@Override
	public ProductResponse updateProduct(ProductRequest productDTO, MultipartFile file) {
		// 수정할 유저 확인
		User user = userRepository.findById(productDTO.getAuthorId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		// 상품 원본 정보 가져오기
		Product product = productRepository.findById(productDTO.getId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		// 관리자 일치여부 확인
		if (!product.getAuthor().getId().equals(user.getId())) {
			throw new IllegalArgumentException("관리자만 수정할 수 있습니다.");			
		}
		// 이미지 수정
		ImageFile savedImage = imageFileService.saveImage(file);
		
		// 수정하지 않을 경우 그대로 놔두기(null)
		if (savedImage != null) product.setImage(savedImage);
		if (productDTO.getName() != null) product.setName(productDTO.getName());
		if (productDTO.getEname() != null) product.setEname(productDTO.getEname());
		if (productDTO.getPrice() != product.getPrice()) product.setPrice(productDTO.getPrice());
		if (productDTO.getIceOrHot() != null) product.setIceOrHot(productDTO.getIceOrHot());
		if (productDTO.getSugar() != null) product.setSugar(productDTO.getSugar());
		if (productDTO.getCaffeine() != null) product.setCaffeine(productDTO.getCaffeine());
		if (productDTO.getCalorie() != null) product.setCalorie(productDTO.getCalorie());
		if (productDTO.getAllergy() != null) product.setAllergy(productDTO.getAllergy());
		if (productDTO.getDetail() != null) product.setDetail(productDTO.getDetail());
		
		Product updatedProduct = productRepository.save(product);
		ProductResponse result = ProductResponse.toDTO(updatedProduct);
		
		return result;
	}


	// 상품 삭제
	@Override
	public ProductResponse deleteProduct(Long id, ProductRequest productDTO) {
		// 수정할 유저 확인
		User user = userRepository.findById(productDTO.getAuthorId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		// 상품 원본 정보 가져오기
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		// 관리자 일치여부 확인
		if (!product.getAuthor().getId().equals(user.getId())) {
			throw new IllegalArgumentException("관리자만 수정할 수 있습니다.");			
		}
		
		productRepository.delete(product);
		
		return ProductResponse.toDTO(product);
	}


	// 상품 검색
	@Override
	public List<ProductResponse> search(String keyword) {
		List<Product> productList = productRepository.findByNameContainsOrEnameContains(keyword, keyword);
		return productList.stream().map(p -> ProductResponse.toDTO(p)).toList();
	}
	
	
	
	
	
	
	
	
	

}
