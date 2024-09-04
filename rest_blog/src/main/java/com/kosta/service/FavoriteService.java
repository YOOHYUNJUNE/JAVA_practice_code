package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.damain.FavoriteRequest;
import com.kosta.damain.FavoriteResponse;

public interface FavoriteService {

	List<FavoriteResponse> getAllPost();

	FavoriteResponse getFavoriteById(Long id);

	FavoriteResponse insertFav(FavoriteRequest fav, MultipartFile file);

	FavoriteResponse updateFav(FavoriteRequest fav, MultipartFile file);

	FavoriteResponse deleteFav(Long id);

}
