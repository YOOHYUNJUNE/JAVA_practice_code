package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.entity.Article;
import com.kosta.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 생성, @autowired 대체
public class BlogServiceImpl implements BlogService {

	private final BlogRepository blogRepository; // autowired 대체

	// 게시물 추가
	@Override
	public Article save(Article article) {		
		return blogRepository.save(article);
	}

	// 게시물 전체 목록
	@Override
	public List<Article> findAll() {
		return blogRepository.findAll();
	}

	// ID로 특정 게시물 찾기
	@Override
	public Article findById(Long id) throws Exception {
		Optional<Article> optArticle = blogRepository.findById(id);
		Article article = optArticle.orElseThrow(() -> new Exception("ID가 없습니다."));
		return article;
	}

	// ID로 특정 게시물 삭제
	@Override
	public void deleteById(Long id) throws Exception {
		Article article = findById(id);
		blogRepository.deleteById(article.getId()); // 없는 ID가 있는 경우 방지
	}

	// 수정
	@Override
	public Article update(Article article) throws Exception {
		Article originArticle = findById(article.getId());
		originArticle.setTitle(article.getTitle());
		originArticle.setContent(article.getContent());
		
		Article updateArticle = blogRepository.save(originArticle);
		return updateArticle;
	}

	// 검색 & 정렬
	@Override
	public List<Article> searchAndOrder(String keyword, String order) {
		if (order.equals("desc")) {
			// 내림 차순			
			return blogRepository.findByTitleContainsOrContentContainsOrderByTitleDesc(keyword, keyword);
		}
		// 오름 차순
		return blogRepository.findByTitleContainsOrContentContainsOrderByTitleAsc(keyword, keyword);	
	}

//	// 검색
//	@Override
//	public List<Article> searchInTitleAndContent(String keyword) {
//		return blogRepository.findByTitleContainsOrContentContains(keyword, keyword);
//	}
//	// 정렬
//	@Override
//	public List<Article> orderingArticleList(String order) {
//		if (order.equals("desc")) return blogRepository.findAllByOrderByTitleDesc();		
//		return blogRepository.findAllByOrderByTitleAsc(); // 기본 오름차순 정렬
//	}
	
	
	
	
}
