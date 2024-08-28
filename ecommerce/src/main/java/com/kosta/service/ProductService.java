package com.kosta.service;

import java.util.List;

import com.kosta.entity.Product;
import com.kosta.entity.User;

public interface ProductService {

	Product save(Product product, User user);

	Product findById(Long id) throws Exception; 

	Product update(Product product, User user) throws Exception;

	void deleteById(Long id, User user) throws Exception;

	List<Product> findAllProduct();

	List<Product> searchAndOrder(String keyword, String order);

}
