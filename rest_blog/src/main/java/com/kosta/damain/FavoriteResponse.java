package com.kosta.damain;

import com.kosta.entity.Favorite;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteResponse {

	private Long id;
	private String title, url;
	private FavoriteDTO imageId;
	
	// Favorite -> FavoriteResponse 변환
	public static FavoriteResponse toDTO(Favorite f) {
		return FavoriteResponse.builder()
				.id(f.getId())
				.title(f.getTitle())
				.url(f.getUrl())
				.imageId(FavoriteDTO.toDTO(f.getImageId()))
				.build();
	}
	
	
}
