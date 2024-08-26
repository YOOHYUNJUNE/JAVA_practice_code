package com.kosta.service;

import com.kosta.entity.Product;
import com.kosta.entity.User;

public interface ProductService {

	Product save(Product product, User user);

	Product findById(Long id);

	Product update(Product product, User user);

	void deleteById(Long id, User user);

}
