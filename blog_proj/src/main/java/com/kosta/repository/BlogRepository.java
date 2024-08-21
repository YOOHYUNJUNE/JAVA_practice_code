package com.kosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Article;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long>{

	List<Article> findByTitleContainsOrContentContains(String keyword1, String keyword2);

	List<Article> findAllByOrderByTitleDesc();

	List<Article> findAllByOrderByTitleAsc();

	
	
}
