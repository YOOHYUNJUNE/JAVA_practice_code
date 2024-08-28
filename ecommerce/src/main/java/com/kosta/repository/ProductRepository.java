package com.kosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameContainsOrderByNameDesc(String keyword);

	List<Product> findByNameContainsOrderByNameAsc(String keyword);

	List<Product> findByTagContainsOrderByTagDesc(String keyword);

	List<Product> findByTagContainsOrderByTagAsc(String keyword);

	
}
