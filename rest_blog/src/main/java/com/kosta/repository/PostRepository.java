package com.kosta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	

}
