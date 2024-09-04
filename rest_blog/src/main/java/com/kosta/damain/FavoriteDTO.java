package com.kosta.damain;

import com.kosta.entity.ImageFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FavoriteDTO {

	private Long id;
	private String origin, saved, kbSize;
	private Long size;
	
	// Favorite -> FavoriteResponse 변환
	public static FavoriteDTO toDTO(ImageFile imageFile) {
		if (imageFile == null) return null;
		return FavoriteDTO.builder()
				.id(imageFile.getId())
				.origin(imageFile.getOriginalName())
				.saved(imageFile.getSavedName())
				.kbSize(((Double) (imageFile.getFileSize() / 1024.0)).toString())
				.build();
	}
	
}
