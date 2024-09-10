package com.kosta.domain.response;

import com.kosta.domain.FavoriteDTO;
import com.kosta.entity.Favorite;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteResponse {

	private Long id;
	private String title, url;
	private FavoriteDTO image;
	
	// Favorite -> FavoriteResponse 변환
	public static FavoriteResponse toDTO(Favorite f) {
		return FavoriteResponse.builder()
				.id(f.getId())
				.title(f.getTitle())
				.url(f.getUrl())
				.image(FavoriteDTO.toDTO(f.getImage()))
				.build();
	}
	
	
}
