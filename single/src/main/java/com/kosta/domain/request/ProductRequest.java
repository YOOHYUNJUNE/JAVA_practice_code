package com.kosta.domain.request;

import com.kosta.entity.ImageFile;
import com.kosta.entity.Product;
import com.kosta.entity.User;

import lombok.Data;

@Data
public class ProductRequest {

	private Long id;
	private String name, ename, iceOrHot, detail, allergy;
	private Double sugar, caffeine, calorie;
	private Integer price;
	private Long authorId;
	private ImageFile imageFile;
	
	public Product toEntity(User author) {
		return Product.builder()
				.name(name)
				.ename(ename)
				.iceOrHot(iceOrHot)
				.detail(detail)
				.allergy(allergy)
				.sugar(sugar)
				.price(price)
				
				.caffeine(caffeine)
				.calorie(calorie)
				.author(author)
				
				.image(imageFile)
				
				.build();
	}
	
}
