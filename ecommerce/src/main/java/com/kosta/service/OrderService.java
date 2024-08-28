package com.kosta.service;

import java.util.List;

import com.kosta.entity.Ordering;
import com.kosta.entity.Product;
import com.kosta.entity.User;

public interface OrderService {

	Ordering save(Ordering ordering, User user);

	List<Ordering> findOrderByUser(Long id);

	void deleteById(Long id, User user) throws Exception;

}
