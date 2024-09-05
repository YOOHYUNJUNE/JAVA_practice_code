package com.kosta.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.damain.FavoriteDTO;
import com.kosta.damain.FavoriteRequest;
import com.kosta.damain.FavoriteResponse;
import com.kosta.entity.Favorite;
import com.kosta.entity.ImageFile;
import com.kosta.repository.FavoriteRepository;
import com.kosta.repository.ImageFileRepository;
import com.kosta.util.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

	private final FavoriteRepository fr;
	private final ImageFileService is;
	
	
	// 전체 즐겨찾기 보기
	@Override
	public List<FavoriteResponse> getAllPost() {
		List<Favorite> favList = fr.findAll();
		
		if (favList.size() > 0) {
			List<FavoriteResponse> favoriteResponseList = favList.stream().map(FavoriteResponse::toDTO).toList();
			return favoriteResponseList;
		} else {
			return new ArrayList<>();
		}		
	}

	
	// 즐겨찾기 ID로 불러오기
	@Override
	public FavoriteResponse getFavoriteById(Long id) {
		Favorite fav = fr.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 즐겨찾기를 찾을 수 없음"));
		FavoriteResponse favoriteResponse = FavoriteResponse.toDTO(fav);
		return favoriteResponse;
		
	}

	

	// 즐겨찾기 추가
	@Override
	public FavoriteResponse insertFav(FavoriteRequest favDTO, MultipartFile file) {
		
		// 이미지 저장
		ImageFile savedImage = is.saveImage(file);
		if (savedImage != null) {
			favDTO.setImageFile(savedImage);
		}
		
		// 즐겨찾기 추가
		Favorite favorite = favDTO.toEntity();
		Favorite savedFavorite = fr.save(favorite);
		FavoriteResponse result = FavoriteResponse.toDTO(savedFavorite);
		
		return result;		
	}

	
	// 즐겨찾기 수정
	@Override
	public FavoriteResponse updateFav(FavoriteRequest favDTO, MultipartFile file) {

		// 원본 즐겨찾기 가져오기
		Favorite favorite = fr.findById(favDTO.getId())
				.orElseThrow(() -> new IllegalArgumentException("해당 즐겨찾기를 찾을 수 없습니다."));
		
		ImageFile savedImage = is.saveImage(file);
		// 수정하지 않을 경우 그대로 놔두기(null로)
		if(savedImage != null) favorite.setImage(savedImage);
		if(favDTO.getTitle() != null) favorite.setTitle(favDTO.getTitle());
		if(favDTO.getUrl() != null) favorite.setUrl(favDTO.getUrl());
		
		Favorite updatedFavorite = fr.save(favorite);
		FavoriteResponse result = FavoriteResponse.toDTO(updatedFavorite);
		return result;		
	}
	

	// 즐겨찾기 삭제
	@Override
	public FavoriteResponse deleteFav(Long id) {

		// 원본 즐겨찾기 가져오기
		Favorite favorite = fr.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 즐겨찾기를 찾을 수 없습니다."));
		
		fr.delete(favorite);
		return FavoriteResponse.toDTO(favorite);
		
	}
	
	

}
