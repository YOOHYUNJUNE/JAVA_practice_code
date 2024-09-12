package com.kosta.service;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.dto.FileDTO;
import com.kosta.entity.ImageFile;

public interface ImageFileService {
	
	public ImageFile saveImage(MultipartFile file);

	FileDTO getImageByImageId(Long id);
	
	
	
	
	
}
