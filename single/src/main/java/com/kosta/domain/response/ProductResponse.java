package com.kosta.domain.response;

import com.kosta.domain.dto.FileDTO;
import com.kosta.entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

	private Long id;
	private String name, ename, iceOrHot, detail, allergy;
	private Double sugar, caffeine, calorie;
	
	private UserResponse author;
	
	private FileDTO image;
	
	
//	Product -> ProductResponse 변환
	public static ProductResponse toDTO(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.ename(product.getEname())
				.iceOrHot(product.getIceOrHot())
				.detail(product.getDetail())
				.allergy(product.getAllergy())
				.sugar(product.getSugar())
				.caffeine(product.getCaffeine())
				.calorie(product.getCalorie())
				.author(UserResponse.toDTO(product.getAuthor()))
				.image(FileDTO.toDTO(product.getImage()))
				.build();
	}
	
	
	
	
}
