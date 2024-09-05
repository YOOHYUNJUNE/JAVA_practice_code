package com.kosta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.damain.FavoriteRequest;
import com.kosta.damain.FavoriteResponse;
import com.kosta.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {

	private final FavoriteService fs;
	
	
	// 모든 즐겨찾기 목록 가져오기
	@GetMapping("")
	public ResponseEntity<List<FavoriteResponse>> getAllFavList(@RequestParam(name = "id", required = false) Long id) {
		List<FavoriteResponse> result = new ArrayList<>();
		
		if (id == null) {
			result = fs.getAllPost();
		} else {
			FavoriteResponse favoriteResponse = fs.getFavoriteById(id);
			result.add(favoriteResponse);
		}
		return ResponseEntity.ok(result);
	}
	
	
	// 즐겨찾기 추가
	@PostMapping("")
	public ResponseEntity<FavoriteResponse> addFav(FavoriteRequest fav, @RequestParam(name = "image", required = false) MultipartFile file) {
		FavoriteResponse savedFav = fs.insertFav(fav, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedFav);
		
	}
	
	// 즐겨찾기 수정
	@PatchMapping("")
	public ResponseEntity<FavoriteResponse> modifyFav(FavoriteRequest fav, @RequestParam(name = "image", required = false) MultipartFile file) {
		
		FavoriteResponse updateFav = fs.updateFav(fav, file);
		return ResponseEntity.ok(updateFav);
	}
	
	
	// 즐겨찾기 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<FavoriteResponse> removeFav(@PathVariable("id") Long id) {
		FavoriteResponse deletedFav = fs.deleteFav(id);
		return ResponseEntity.ok(deletedFav);
	}
	
	
	
}
