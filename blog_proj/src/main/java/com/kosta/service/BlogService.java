package com.kosta.service;

import java.util.List;

import com.kosta.entity.Article;

public interface BlogService {

	Article save(Article article); // 접근 지정자 없음
	
	// 모든 게시물 목록
	List<Article> findAll();
	
	// ID로 특정 게시물 찾기
	Article findById(Long id) throws Exception;
	
	// ID로 특정 게시물 삭제
	void deleteById(Long id) throws Exception;
	
	// ID로 특정 게시물 수정
	Article update(Article article) throws Exception;
	
	// 검색
	List<Article> searchInTitleAndContent(String keyword);

	// 정렬
	List<Article> orderingArticleList(String order);
	
}
