package com.kosta.service;

import java.util.List;

import com.kosta.entity.BlogEntity;

public interface BlogService {

	BlogEntity save(BlogEntity blogEntity) throws Exception;

	List<BlogEntity> findAll();

	BlogEntity findById(int id) throws Exception;

	void deleteById(int id) throws Exception;

	BlogEntity update(BlogEntity blogEntity) throws Exception;

}
