package com.kosta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
