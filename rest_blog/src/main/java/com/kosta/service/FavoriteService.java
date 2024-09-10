package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.FavoriteRequest;
import com.kosta.domain.response.FavoriteResponse;

public interface FavoriteService {

	List<FavoriteResponse> getAllFavorite();

	FavoriteResponse getFavoriteById(Long id);

	FavoriteResponse insertFav(FavoriteRequest fav, MultipartFile file);

	FavoriteResponse updateFav(FavoriteRequest fav, MultipartFile file);

	FavoriteResponse deleteFav(Long id);

}
